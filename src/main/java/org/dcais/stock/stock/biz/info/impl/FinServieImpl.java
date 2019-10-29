package org.dcais.stock.stock.biz.info.impl;

import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.info.FinService;
import org.dcais.stock.stock.biz.tushare.FinInfoService;
import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.common.utils.MathUtil;
import org.dcais.stock.stock.common.utils.ReflectUtils;
import org.dcais.stock.stock.dao.xdriver.fin.XFinIncomeDao;
import org.dcais.stock.stock.dao.xdriver.fin.XFinIndicatorDao;
import org.dcais.stock.stock.dao.xdriver.fin.XFinIndicatorRateDao;
import org.dcais.stock.stock.entity.info.FinIncome;
import org.dcais.stock.stock.entity.info.FinIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
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
  @Override
  public Result syncFinIncome(String tsCode) {
    log.info("sync fin income "+tsCode);
    xFinIncomeDao.remove(tsCode);

    Result r = finInfoService.finIncome(tsCode);
    if(!r.isSuccess()){
      log.error(r.getErrorMsg());
      return r;
    }

    List<FinIncome> finIncomes = (List<FinIncome>) r.getData();

    xFinIncomeDao.insertList(finIncomes);
    return Result.wrapSuccessfulResult("");
  }

  @Override
  public Result syncFinIndicator(String tsCode) {
    log.info("sync fin indicator"+tsCode);
    xFinIncomeDao.remove(tsCode);

    Result r = finInfoService.finIndicator(tsCode);
    if(!r.isSuccess()){
      log.error(r.getErrorMsg());
      return r;
    }

    List<FinIndicator> finIndicators= (List<FinIndicator>) r.getData();

    xFinIndicatorDao.insertList(finIndicators);
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
            rate = MathUtil.divide(diff,last);
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
    Map<String,FinIndicator> mapLastYear = finIndicatorsLastYear.stream().collect(Collectors.toMap(FinIndicator::getTsCode, Function.identity()));
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
}
