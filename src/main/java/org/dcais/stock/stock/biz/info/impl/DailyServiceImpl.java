package org.dcais.stock.stock.biz.info.impl;


import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.BaseServiceImpl;
import org.dcais.stock.stock.biz.basic.BasicService;
import org.dcais.stock.stock.biz.info.DailyService;
import org.dcais.stock.stock.biz.tushare.StockInfoService;
import org.dcais.stock.stock.common.cons.CmnConstants;
import org.dcais.stock.stock.common.cons.MetaContants;
import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.common.utils.CommonUtils;
import org.dcais.stock.stock.common.utils.DateUtils;
import org.dcais.stock.stock.common.utils.ListUtil;
import org.dcais.stock.stock.dao.mybatis.info.DailyDao;
import org.dcais.stock.stock.dao.xdriver.meta.XMetaCollDao;
import org.dcais.stock.stock.entity.basic.Basic;
import org.dcais.stock.stock.entity.info.Daily;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;


@Slf4j
@Service
public class DailyServiceImpl extends BaseServiceImpl implements DailyService {

  @Autowired
  private DailyDao dailyDao;
  @Autowired
  private BasicService basicService;
  @Autowired
  private StockInfoService stockInfoService;
  @Value("${stock.batch-insert-size:1000}")
  private Integer batchInsertSize;
  @Autowired
  private XMetaCollDao xMetaCollDao;

  public List<Daily> getAll() {
    return super.getAll(dailyDao);
  }

  public List<Daily> select(Map<String,Object> param){
    return dailyDao.select(param);
  }

  public Integer selectCount(Map<String,Object> param){
    return dailyDao.selectCount(param);
  }

  public Daily getById(Long id) {
    return super.getById(dailyDao, id);
  }

  public boolean save(Daily daily) {
    return super.save(dailyDao, daily);
  }

  public boolean deleteById(Long id) {
    return super.deleteById(dailyDao, id);
  }

  public int deleteByIds(Long[] ids) {
    return super.deleteByIds(dailyDao, ids);
  }

  @Override
  public void syncAll(String mode){
    if( CmnConstants.SYNC_MODE_SYMBOL.equals(mode)){
      List<Basic> basics = basicService.getAllList();
      basics.forEach(this::syncHistory);
    }
    else if (CmnConstants.SYNC_MODE_DATE.equals(mode)){
      Object objValue= xMetaCollDao.get(MetaContants.META_KEY_DAILY_LAST_SYNC_DATE);
      if(objValue == null){
        throw new RuntimeException( MetaContants.META_KEY_DAILY_LAST_SYNC_DATE+"not set");
      }
      Date minLastTradeDate = CommonUtils.getValue(objValue,Date.class);
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(minLastTradeDate);
      calendar.add(Calendar.DATE,1);
      Date tradeDateStart = calendar.getTime();
      dailyDao.deleteDateAfterDate(tradeDateStart);
      this.syncHistryFromTradeDate(tradeDateStart);
    }else {
      throw new RuntimeException("unknown sync mode"+mode);
    }
  }

  public Result batchInsert(List<Daily> dailyList){
    List<List<Daily>> subList = ListUtil.getSubDepartList(dailyList,batchInsertSize);
    if(ListUtil.isBlank(subList)){
      return Result.wrapSuccessfulResult("");
    }
    for(int i= subList.size()-1 ; i>=0 ; i-- ){
      List<Daily> dailies = subList.get(i);
      if(ListUtil.isBlank(dailies)){
        continue;
      }

      List<Daily> tmps = new ArrayList<>(dailies.size());
      for(int j=dailies.size()-1 ; j>=0 ; j --){
        Daily daily = dailies.get(j);
        daily.setDefaultBizValue();
        tmps.add(daily);
      }

      dailyDao.batchInsert(tmps);
    }
    return Result.wrapSuccessfulResult("");
  }

  public Result syncHistryFromTradeDate(Date fromTradeDate){
    Calendar ca = Calendar.getInstance();
    ca.setTime(fromTradeDate);
    Date now = new Date();
    Date today = DateUtils.getEndTimeDate(now);
    Date tradeDate = ca.getTime();
    Result r = Result.wrapSuccessfulResult("OK");
    while (tradeDate.before(today)){
      r = syncHistorySingleDate(tradeDate);
      if(!r.isSuccess()){
        break;
      }
      xMetaCollDao.put(MetaContants.META_KEY_DAILY_LAST_SYNC_DATE,tradeDate);
      ca.add(Calendar.DATE , 1);
      tradeDate = ca.getTime();
    }
    return r;
  }

  public Result syncHistorySingleDate(Date tradeDate){
    Result result  = stockInfoService.daily(null,tradeDate,null,null);
    if(!result.isSuccess()){
      log.error(result.getErrorMsg());
      return result;
    }
    List<Daily> dailyList = (List<Daily>) result.getData();
    return this.batchInsert(dailyList);
  }

  @Override
  public Result syncHistory(String symbol) {
      Basic basic = basicService.getBySymbol(symbol);
      if(basic == null){
          String errMsg = "cannot find symbol "+ symbol;
          log.error(errMsg);
          return Result.wrapErrorResult("",errMsg);
      }
      return syncHistory(basic);
  }

  private Result syncHistory(Basic basic){
      Daily dailyMaxInDb = null;
      Date startDate = null;
      List<Daily> lastestDailys = dailyDao.getMaxDaily(basic.getTsCode());
      if(ListUtil.isNotBlank(lastestDailys)){
          dailyMaxInDb = lastestDailys.get(0);
          Calendar c = Calendar.getInstance();
          c.setTime(dailyMaxInDb.getTradeDate());
          c.add(Calendar.DATE,1);
          startDate =  c.getTime();
      }
      if(startDate == null){
          startDate = basic.getListDate();
      }

      Date today = DateUtils.getStartTimeDate(new Date());
      while ( startDate.before(today)){

          Calendar calendar = Calendar.getInstance();
          calendar.setTime(startDate);
          calendar.add(calendar.YEAR, 2);
          Date endDate = calendar.getTime();
          Result result = stockInfoService.daily(basic.getTsCode(),null,startDate,endDate);
          if(!result.isSuccess()){
             log.error(result.getErrorMsg());
             break;
          }
          startDate = endDate;
          List<Daily> dailyList = (List<Daily>) result.getData();
          this.batchInsert(dailyList);
      }

      return Result.wrapSuccessfulResult("OK");
  }

  private void dealWithSync(Daily daily){
      Map<String,Object> param = new HashMap<>();
      param.put("isDeleted","N");
      param.put("tsCode",daily.getTsCode());
      param.put("tradeDate", DateUtils.formatDate(daily.getTradeDate(),DateUtils.Y_M_D));
      List tmps = this.select(param);
      if(ListUtil.isNotBlank(tmps)){
          return;
      }
      this.save(daily);
  }



}
