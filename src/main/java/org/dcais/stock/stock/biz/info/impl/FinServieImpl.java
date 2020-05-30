package org.dcais.stock.stock.biz.info.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.info.FinService;
import org.dcais.stock.stock.biz.info.IFinIndicatorService;
import org.dcais.stock.stock.biz.tushare.FinInfoService;
import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.common.utils.*;
import org.dcais.stock.stock.dao.xdriver.fin.XFinIncomeDao;
import org.dcais.stock.stock.dao.xdriver.fin.XFinIndicatorDao;
import org.dcais.stock.stock.dao.xdriver.fin.XFinIndicatorRateDao;
import org.dcais.stock.stock.entity.info.FinIncome;
import org.dcais.stock.stock.entity.info.FinIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ZeroCopyHttpOutputMessage;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FinServieImpl implements FinService {
  @Autowired
  private FinInfoService finInfoService;
  @Autowired
  private XFinIncomeDao xFinIncomeDao;
  @Autowired
  private XFinIndicatorDao xFinIndicatorDao;
  @Autowired
  private XFinIndicatorRateDao xFinIndicatorRateDao;

  @Autowired
  private IFinIndicatorService finIndicatorService;

  @Override
  public Result syncFinIncome(String tsCode) {
    log.info("sync fin income "+tsCode);
    Date startDate = null;
    FinIncome lastData = xFinIncomeDao.getLast(tsCode);
    if(lastData != null){
      Date lastUpdate = LocalDateUtils.asDate(lastData.getEndDate());
      Calendar ca = Calendar.getInstance();
      ca.setTime(lastUpdate);
      ca.add(Calendar.DATE,1);
      startDate = ca.getTime();
    }

    Result r = finInfoService.finIncome(tsCode,startDate);
    if(!r.isSuccess()){
      log.error(r.getErrorMsg());
      return r;
    }

    List<FinIncome> finIncomes = (List<FinIncome>) r.getData();
    if(!ListUtil.isBlank(finIncomes)){
      xFinIncomeDao.insertList(finIncomes);
    }

    return Result.wrapSuccessfulResult("");
  }

  @Override
  public Result syncFinIndicator(String tsCode) {
    log.info("sync fin indicator"+tsCode);
    xFinIndicatorDao.remove(tsCode);
    FinIndicator lastData = finIndicatorService.getOne(
      Wrappers.<FinIndicator>lambdaQuery()
        .eq(FinIndicator::getTsCode, tsCode)
        .orderByDesc(FinIndicator::getReportYear,FinIndicator::getReportSeason)
        .last("limit 1")
    );

    LocalDateTime startDate = null;
    if(lastData != null){
      LocalDateTime lastUpdate =  lastData.getEndDate();
      startDate = lastUpdate.plusDays(1);
    }

    Result r = finInfoService.finIndicator(tsCode,startDate);
    if(!r.isSuccess()){
      log.error(r.getErrorMsg());
      return r;
    }

    List<FinIndicator> finIndicators= (List<FinIndicator>) r.getData();
    if( ListUtil.isBlank(finIndicators) == false ){
      finIndicatorService.saveBatch(finIndicators);
    }

    return Result.wrapSuccessfulResult("");
  }

  public FinIndicator calcFinIndcatorRate(FinIndicator dataNow, FinIndicator dataLast){
    List<Field> targetFields = ReflectUtils.getFieldList(FinIndicator.class);
    FinIndicator dataRate = new FinIndicator();

    for( Field field : targetFields){
      Method setMethod = ReflectUtils.getSetMethod(field, dataRate);
      if (setMethod == null) {
        continue;
      }
      Method getMethod = ReflectUtils.getGetMethod(field.getName(), dataNow);
      try {
        if( BigDecimal.class.isAssignableFrom(field.getType())== false ){
          setMethod.invoke(dataRate,getMethod.invoke(dataNow, null));
        }else{
          BigDecimal last = (BigDecimal) getMethod.invoke(dataLast,null);
          if(last == null){
            continue;
          }
          BigDecimal now = (BigDecimal) getMethod.invoke(dataNow, null);
          if(now == null){
            continue;
          }
          BigDecimal diff = MathUtil.subtract(now,last);
          BigDecimal rate = null;
          if(BigDecimal.ZERO.compareTo(diff) == 0){
            rate = BigDecimal.ZERO;
          }
          else if(BigDecimal.ZERO.compareTo(last) == 0){
            rate = null;
          }else{
            rate = MathUtil.divide(diff,last.abs());
          }
          setMethod.invoke(dataRate,rate);
        }

      } catch (IllegalAccessException | InvocationTargetException e) {
        log.error("",e);
      }

    }
    return dataRate;

  }

  @Override
  public Result calcFinIndicatorRate(int reportYear, int reportSeason){
    List<FinIndicator> finIndicators =xFinIndicatorDao.get(reportYear,reportSeason);
    List<FinIndicator> finIndicatorsLastYear = xFinIndicatorDao.get(reportYear-1,reportSeason);
    Map<String,FinIndicator> mapLastYear = finIndicatorsLastYear.stream().collect(Collectors.toMap(FinIndicator::getTsCode, Function.identity(), (t1,t2)-> t1));
    List<FinIndicator> rates = finIndicators.stream().map( dataNow->{
      FinIndicator dataLast = mapLastYear.get(dataNow.getTsCode());
      if(dataLast == null){
        return null;
      }
      FinIndicator dataRate = this.calcFinIndcatorRate(dataNow,dataLast);
      return dataRate;

    }).filter( t-> t!=null).collect(Collectors.toList());
    xFinIndicatorRateDao.remove(reportYear,reportSeason);
    xFinIndicatorRateDao.insertList(rates);
    return Result.wrapSuccessfulResult("OK");
  }

  public Map<String,Integer> getPrevYearAndSeason(int reportYear, int reportSeason){
    Integer retReportSeason = reportSeason -1;
    Integer retReportYear = reportYear;
    if(retReportSeason== 0){
      retReportYear = retReportYear -1;
      retReportSeason = 4;
    }
    Map<String,Integer> ret = new HashMap();
    ret.put("reportYear",retReportYear);
    ret.put("reportSeason",retReportSeason);
    return ret;

  }

  public boolean checkFinIncrease(String tsCode, int reportYear, int reportSeason ){
    FinIndicator finIndicatorRate = xFinIndicatorRateDao.get(tsCode,reportYear,reportSeason);
    FinIndicator finIndicator = xFinIndicatorDao.get(tsCode,reportYear,reportSeason);
    if(finIndicator == null){
      return false;
    }
    if(finIndicatorRate == null ){
      return false;
    }
    if(finIndicatorRate.getEps() == null){
      return false;
    }
    if(finIndicatorRate.getEps().compareTo(new BigDecimal(0.17))<0){
      return false;
    }
    if(finIndicatorRate.getTotalRevenuePs() == null){
      return false;
    }
    if(finIndicatorRate.getTotalRevenuePs().compareTo(new BigDecimal(0.17))<0){
      return false;
    }
    if(finIndicator.getRoe() == null){
      return false;
    }
    if(finIndicator.getRoe().compareTo(new BigDecimal(0.15))<0) {
      return false;
    }
    if(finIndicator.getCfps() == null){
      return false;
    }
    if(finIndicator.getEps() == null) {
      return false;
    }
    if(finIndicator.getEps().compareTo(BigDecimal.ZERO) <= 0 ){
      return false;
    }
    if(MathUtil.divide(finIndicator.getCfps(),finIndicator.getEps()).compareTo(new BigDecimal(0.17))<0){
      return false;
    }
    return true;
  }

  @Override
  public Result getCANSLIMCandidates(int reportYear, int reportSeason){
    List<FinIndicator> rateCurSeason = xFinIndicatorDao.get(reportYear,reportSeason);
    List<String> tsCodes = rateCurSeason.stream()
//      .filter(t->{ return t.getEps() != null && t.getEps().compareTo(BigDecimal.ONE) > 0; })
      .map(FinIndicator::getTsCode).collect(Collectors.toList());
    List<String> targets = new ArrayList<>();
    for(String tsCode : tsCodes){
      int seasonCnt = 4 ;
      int checkReportYear = reportYear;
      int checkReportSeason = reportSeason;
      boolean bResult = false;
      do {
        bResult = checkFinIncrease(tsCode,checkReportYear,checkReportSeason);
        Map<String,Integer> mapNext = this.getPrevYearAndSeason(checkReportYear,checkReportSeason);
        checkReportYear = mapNext.get("reportYear");
        checkReportSeason = mapNext.get("reportSeason");
        seasonCnt --;
        if(bResult == false){
          break;
        }
      }while (seasonCnt >= 0);
      if (bResult == false){
        continue;
      }

      int yearCnt = 1;
      checkReportYear = reportYear;
      checkReportSeason = reportSeason;
      bResult = false;
      do {
        bResult = checkFinIncrease(tsCode,checkReportYear,checkReportSeason);
        checkReportYear = checkReportYear -1;
        yearCnt --;
        if(bResult == false){
          break;
        }
      }while (yearCnt>= 0);
      if (bResult == false){
        continue;
      }

      targets.add(tsCode);
    }
    return Result.wrapSuccessfulResult(targets);
  }
}
