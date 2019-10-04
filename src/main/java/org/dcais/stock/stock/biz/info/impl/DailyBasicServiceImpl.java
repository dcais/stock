package org.dcais.stock.stock.biz.info.impl;


import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.BaseServiceImpl;
import org.dcais.stock.stock.biz.basic.BasicService;
import org.dcais.stock.stock.biz.info.DailyBasicService;
import org.dcais.stock.stock.biz.info.DailyService;
import org.dcais.stock.stock.biz.tushare.StockInfoService;
import org.dcais.stock.stock.common.cons.CmnConstants;
import org.dcais.stock.stock.common.cons.MetaContants;
import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.common.utils.CommonUtils;
import org.dcais.stock.stock.common.utils.DateUtils;
import org.dcais.stock.stock.common.utils.ListUtil;
import org.dcais.stock.stock.dao.mybatis.info.DailyDao;
import org.dcais.stock.stock.dao.xdriver.daily.XDailyBasicDao;
import org.dcais.stock.stock.dao.xdriver.meta.XMetaCollDao;
import org.dcais.stock.stock.entity.basic.Basic;
import org.dcais.stock.stock.entity.info.Daily;
import org.dcais.stock.stock.entity.info.DailyBasic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;


@Slf4j
@Service
public class DailyBasicServiceImpl implements DailyBasicService {

  @Autowired
  private XDailyBasicDao xDailyBasicDao;
  @Autowired
  private BasicService basicService;
  @Autowired
  private StockInfoService stockInfoService;
  @Value("${stock.batch-insert-size:1000}")
  private Integer batchInsertSize;
  @Autowired
  private XMetaCollDao xMetaCollDao;

  @Override
  public void syncAll(String mode) {
    if (CmnConstants.SYNC_MODE_SYMBOL.equals(mode)) {
      List<Basic> basics = basicService.getAllList();
      basics.forEach(this::syncHistory);
    } else if (CmnConstants.SYNC_MODE_DATE.equals(mode)) {
      Object objValue = xMetaCollDao.get(MetaContants.META_KEY_DAILY_BASIC_LAST_SYNC_DATE);
      if (objValue == null) {
        throw new RuntimeException(MetaContants.META_KEY_DAILY_BASIC_LAST_SYNC_DATE+ " not set");
      }
      Date minLastTradeDate = CommonUtils.getValue(objValue, Date.class);
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(minLastTradeDate);
      calendar.add(Calendar.DATE, 1);
      Date tradeDateStart = calendar.getTime();
      xDailyBasicDao.deleteGte(tradeDateStart);
      this.syncHistryFromTradeDate(tradeDateStart);
    } else {
      throw new RuntimeException("unknown sync mode" + mode);
    }
  }

  public Result batchInsert(List<DailyBasic> dailyList) {
    List<List<DailyBasic>> subList = ListUtil.getSubDepartList(dailyList, batchInsertSize);
    if (ListUtil.isBlank(subList)) {
      return Result.wrapSuccessfulResult("");
    }
    for (int i = subList.size() - 1; i >= 0; i--) {
      List<DailyBasic> dailies = subList.get(i);
      if (ListUtil.isBlank(dailies)) {
        continue;
      }

      List<DailyBasic> tmps = new ArrayList<>(dailies.size());
      for (int j = dailies.size() - 1; j >= 0; j--) {
        DailyBasic daily = dailies.get(j);
        daily.setDefaultBizValue();
        tmps.add(daily);
      }

      xDailyBasicDao.insertList(tmps);
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
    Result result = stockInfoService.dailyBasic(null, tradeDate, null, null);
    if (!result.isSuccess()) {
      log.error(result.getErrorMsg());
      return result;
    }
    List<DailyBasic> dailyList = (List<DailyBasic>) result.getData();
    Result r = this.batchInsert(dailyList);
    if (r.isSuccess()) {
      if (dailyList.size() > 0) {
        xMetaCollDao.put(MetaContants.META_KEY_DAILY_LAST_SYNC_DATE, tradeDate);
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
    Date startDate = null;
    DailyBasic dailyBasicLatest = xDailyBasicDao.getLatest(basic.getTsCode());
    if (dailyBasicLatest != null) {
      Calendar c = Calendar.getInstance();
      c.setTime(dailyBasicLatest.getTradeDate());
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

      Date nextStartDate = calendar.getTime();
      calendar.add(calendar.DATE, -1);

      Date endDate = calendar.getTime();
      Result result = stockInfoService.dailyBasic(basic.getTsCode(), null, startDate, endDate);
      if (!result.isSuccess()) {
        log.error(result.getErrorMsg());
        break;
      }
      
      startDate = nextStartDate;
      List<DailyBasic> dailyList = (List<DailyBasic>) result.getData();
      this.batchInsert(dailyList);
    }

    return Result.wrapSuccessfulResult("OK");
  }



}
