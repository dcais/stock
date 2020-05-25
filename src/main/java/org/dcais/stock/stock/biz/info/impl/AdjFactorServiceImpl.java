package org.dcais.stock.stock.biz.info.impl;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.IBaseServiceImpl;
import org.dcais.stock.stock.biz.basic.IBasicService;
import org.dcais.stock.stock.biz.info.IAdjFactorService;
import org.dcais.stock.stock.biz.tushare.StockInfoService;
import org.dcais.stock.stock.common.cons.CmnConstants;
import org.dcais.stock.stock.common.cons.MetaContants;
import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.common.utils.CommonUtils;
import org.dcais.stock.stock.common.utils.DateUtils;
import org.dcais.stock.stock.common.utils.ListUtil;
import org.dcais.stock.stock.common.utils.LocalDateUtils;
import org.dcais.stock.stock.dao.mybatisplus.info.AdjFactorMapper;
import org.dcais.stock.stock.dao.xdriver.meta.XMetaCollDao;
import org.dcais.stock.stock.entity.basic.Basic;
import org.dcais.stock.stock.entity.info.AdjFactor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.util.*;

@Slf4j
@Service
public class AdjFactorServiceImpl extends IBaseServiceImpl<AdjFactorMapper, AdjFactor> implements IAdjFactorService {

  @Autowired
  private AdjFactorMapper adjFactorMapper;
  @Autowired
  private StockInfoService stockInfoService;
  @Autowired
  private IBasicService basicService;
  @Autowired
  private XMetaCollDao xMetaCollDao;

  @Value("${stock.batch-insert-size:1000}")
  private Integer batchInsertSize;


  @Override
  public void syncAll(String mode) {
    if (CmnConstants.SYNC_MODE_SYMBOL.equals(mode)) {
      List<Basic> basics = basicService.getAllList();
      basics.forEach(this::syncHistory);
    } else if (CmnConstants.SYNC_MODE_DATE.equals(mode)) {
      Object objValue = xMetaCollDao.get(MetaContants.META_KEY_ADFACTOR_LAST_SYNC_DATE);
      if (objValue == null) {
        throw new RuntimeException(MetaContants.META_KEY_ADFACTOR_LAST_SYNC_DATE + " not set");
      }
      Date minLastTradeDate = CommonUtils.getValue(objValue, Date.class);
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(minLastTradeDate);
      calendar.add(Calendar.DATE, 1);
      Date tradeDateStart = calendar.getTime();
      adjFactorMapper.deleteDateAfterDate(tradeDateStart);
      this.syncHistryFromTradeDate(tradeDateStart);
    }
  }

  public Result batchInsert(List<AdjFactor> dailyList) {
    this.saveBatch(dailyList,batchInsertSize);
    return Result.wrapSuccessfulResult("");
  }

  public Result syncHistryFromTradeDate(Date fromTradeDate) {
    Calendar ca = Calendar.getInstance();
    ca.setTime(fromTradeDate);
    Date now = new Date();
    Date today = DateUtils.getEndTimeDate(now);
    Date tradeDate = ca.getTime();
    Result r = Result.wrapSuccessfulResult("OK");
    while (tradeDate.before(today)) {
      r = syncHistorySingleDate(tradeDate);
      if (!r.isSuccess()) {
        break;
      }
      ca.add(Calendar.DATE, 1);
      tradeDate = ca.getTime();
    }
    return r;
  }

  public Result syncHistorySingleDate(Date tradeDate) {
    Result result = stockInfoService.adjFactor(null, tradeDate, null, null);
    if (!result.isSuccess()) {
      log.error(result.getErrorMsg());
      return result;
    }
    List<AdjFactor> dailyList = (List<AdjFactor>) result.getData();

    Result r = this.batchInsert(dailyList);
    if (r.isSuccess()) {
      if (dailyList.size() > 0) {
        xMetaCollDao.put(MetaContants.META_KEY_ADFACTOR_LAST_SYNC_DATE, tradeDate);
      }
    }
    return r;
  }

  @Override
  public Result syncHistory(String symbol) {
    Basic basic = basicService.getBySymbol(symbol);
    if (basic == null) {
      String errMsg = "cannot find symbol " + symbol;
      log.error(errMsg);
      return Result.wrapErrorResult("", errMsg);
    }
    return syncHistory(basic);
  }

  private Result syncHistory(Basic basic) {
    AdjFactor dailyMaxInDb = null;
    Date startDate = null;
    Result rTmp = this.getMaxDaily(basic.getTsCode());
    if (rTmp.isSuccess()) {
      dailyMaxInDb = (AdjFactor) rTmp.getData();
      Calendar c = Calendar.getInstance();
      c.setTime(LocalDateUtils.asDate(dailyMaxInDb.getTradeDate()));
      c.add(Calendar.DATE, 1);
      startDate = c.getTime();
    }
    if (startDate == null) {
      startDate = LocalDateUtils.asDate(basic.getListDate());
    }

    Date today = DateUtils.getStartTimeDate(new Date());
    while (startDate.before(today)) {

      Calendar calendar = Calendar.getInstance();
      calendar.setTime(startDate);
      calendar.add(calendar.YEAR, 2);

      Date nextStartDate = calendar.getTime();
      calendar.add(calendar.DATE, -1);

      Date endDate = calendar.getTime();
      Result result = stockInfoService.adjFactor(basic.getTsCode(), null, startDate, endDate);
      if (!result.isSuccess()) {
        log.error(result.getErrorMsg());
        break;
      }
      startDate = nextStartDate;
      List<AdjFactor> dailyList = (List<AdjFactor>) result.getData();
      this.batchInsert(dailyList);
    }

    return Result.wrapSuccessfulResult("OK");
  }

  private void dealWithSync(AdjFactor factor) {
    Map<String, Object> param = new HashMap<>();
    List tmps = this.list(Wrappers.<AdjFactor>lambdaQuery()
      .eq(AdjFactor::getTsCode, factor.getTsCode())
      .eq(AdjFactor::getTradeDate, LocalDateUtils.formatToStr(factor.getTradeDate(),LocalDateUtils.Y_M_D))
    );
    if (ListUtil.isNotBlank(tmps)) {
      return;
    }
    this.save(factor);
  }

  @Override
  public Result<AdjFactor> getMaxDaily(String tsCode){
    List<AdjFactor> adjFactors = this.list(Wrappers.<AdjFactor>lambdaQuery()
      .eq(AdjFactor::getTsCode,tsCode)
      .orderByDesc(AdjFactor::getTradeDate)
      .last("limit 1")
    );
    if(ListUtil.isBlank(adjFactors)){
      return Result.wrapErrorResult("","cannot find max adjFactor");
    }
    return Result.wrapSuccessfulResult(adjFactors.get(0));
  }
}
