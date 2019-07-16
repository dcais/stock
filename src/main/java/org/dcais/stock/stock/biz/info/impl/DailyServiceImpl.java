package org.dcais.stock.stock.biz.info.impl;


import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.BaseServiceImpl;
import org.dcais.stock.stock.biz.basic.BasicService;
import org.dcais.stock.stock.biz.info.DailyService;
import org.dcais.stock.stock.biz.tushare.StockInfoService;
import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.common.utils.DateUtils;
import org.dcais.stock.stock.common.utils.ListUtil;
import org.dcais.stock.stock.dao.info.DailyDao;
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
  public void syncAll(){
      List<Basic> basics = basicService.getAllList();
      basics.forEach(this::syncHistory);
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
          startDate = dailyMaxInDb.getTradeDate();
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
          List<List<Daily>> subList = ListUtil.getSubDepartList(dailyList,batchInsertSize);
          if(ListUtil.isBlank(subList)){
            continue;
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
