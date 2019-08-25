package org.dcais.stock.stock.biz.info.impl;


import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.BaseServiceImpl;
import org.dcais.stock.stock.biz.basic.BasicService;
import org.dcais.stock.stock.biz.info.AdjFactorService;
import org.dcais.stock.stock.biz.tushare.StockInfoService;
import org.dcais.stock.stock.common.cons.CmnConstants;
import org.dcais.stock.stock.common.cons.MetaContants;
import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.common.utils.CommonUtils;
import org.dcais.stock.stock.common.utils.DateUtils;
import org.dcais.stock.stock.common.utils.ListUtil;
import org.dcais.stock.stock.dao.mybatis.info.AdjFactorDao;
import org.dcais.stock.stock.dao.xdriver.meta.XMetaCollDao;
import org.dcais.stock.stock.entity.basic.Basic;
import org.dcais.stock.stock.entity.info.AdjFactor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class AdjFactorServiceImpl extends BaseServiceImpl implements AdjFactorService {

  @Autowired
  private AdjFactorDao adjFactorDao;
  @Autowired
  private StockInfoService stockInfoService;
  @Autowired
  private BasicService basicService;

  @Value("${stock.batch-insert-size:1000}")
  private Integer batchInsertSize;

  @Autowired
  private XMetaCollDao xMetaCollDao;

  public List<AdjFactor> getAll() {
    return super.getAll(adjFactorDao);
  }

  public List<AdjFactor> select(Map<String, Object> param) {
    return adjFactorDao.select(param);
  }

  public Integer selectCount(Map<String, Object> param) {
    return adjFactorDao.selectCount(param);
  }

  public AdjFactor getById(Long id) {
    return super.getById(adjFactorDao, id);
  }

  public boolean save(AdjFactor adjFactor) {
    return super.save(adjFactorDao, adjFactor);
  }

  public boolean deleteById(Long id) {
    return super.deleteById(adjFactorDao, id);
  }

  public int deleteByIds(Long[] ids) {
    return super.deleteByIds(adjFactorDao, ids);
  }


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
      adjFactorDao.deleteDateAfterDate(tradeDateStart);
      this.syncHistryFromTradeDate(tradeDateStart);
    }
  }

  public Result batchInsert(List<AdjFactor> dailyList) {
    List<List<AdjFactor>> subList = ListUtil.getSubDepartList(dailyList, batchInsertSize);
    if (ListUtil.isBlank(subList)) {
      return Result.wrapSuccessfulResult("");
    }
    for (int i = subList.size() - 1; i >= 0; i--) {
      List<AdjFactor> dailies = subList.get(i);
      if (ListUtil.isBlank(dailies)) {
        continue;
      }

      List<AdjFactor> tmps = new ArrayList<>(dailies.size());
      for (int j = dailies.size() - 1; j >= 0; j--) {
        AdjFactor daily = dailies.get(j);
        daily.setDefaultBizValue();
        tmps.add(daily);
      }

      adjFactorDao.batchInsert(tmps);
    }
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
    List<AdjFactor> lastestFactors = adjFactorDao.getMaxDaily(basic.getTsCode());
    if (ListUtil.isNotBlank(lastestFactors)) {
      dailyMaxInDb = lastestFactors.get(0);
      Calendar c = Calendar.getInstance();
      c.setTime(dailyMaxInDb.getTradeDate());
      c.add(Calendar.DATE, 1);
      startDate = c.getTime();
    }
    if (startDate == null) {
      startDate = basic.getListDate();
    }

    Date today = DateUtils.getStartTimeDate(new Date());
    while (startDate.before(today)) {

      Calendar calendar = Calendar.getInstance();
      calendar.setTime(startDate);
      calendar.add(calendar.YEAR, 2);
      Date endDate = calendar.getTime();
      Result result = stockInfoService.adjFactor(basic.getTsCode(), null, startDate, endDate);
      if (!result.isSuccess()) {
        log.error(result.getErrorMsg());
        break;
      }
      startDate = endDate;
      List<AdjFactor> dailyList = (List<AdjFactor>) result.getData();
      this.batchInsert(dailyList);
    }

    return Result.wrapSuccessfulResult("OK");
  }

  private void dealWithSync(AdjFactor factor) {
    Map<String, Object> param = new HashMap<>();
    param.put("isDeleted", "N");
    param.put("tsCode", factor.getTsCode());
    param.put("tradeDate", DateUtils.formatDate(factor.getTradeDate(), DateUtils.Y_M_D));
    List tmps = this.select(param);
    if (ListUtil.isNotBlank(tmps)) {
      return;
    }
    this.save(factor);
  }
}
