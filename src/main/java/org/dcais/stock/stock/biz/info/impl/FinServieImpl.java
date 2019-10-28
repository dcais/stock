package org.dcais.stock.stock.biz.info.impl;

import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.info.FinService;
import org.dcais.stock.stock.biz.tushare.FinInfoService;
import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.common.utils.DateUtils;
import org.dcais.stock.stock.dao.xdriver.fin.XFinIncomeDao;
import org.dcais.stock.stock.dao.xdriver.fin.XFinIndicatorDao;
import org.dcais.stock.stock.entity.info.FinIncome;
import org.dcais.stock.stock.entity.info.FinIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class FinServieImpl implements FinService {
  @Autowired
  private FinInfoService finInfoService;
  @Autowired
  private XFinIncomeDao xFinIncomeDao;
  @Autowired
  private XFinIndicatorDao xFinIndicatorDao;

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
}
