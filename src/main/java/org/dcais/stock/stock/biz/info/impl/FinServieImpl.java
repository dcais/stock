package org.dcais.stock.stock.biz.info.impl;

import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.info.FinService;
import org.dcais.stock.stock.biz.tushare.FinInfoService;
import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.common.utils.DateUtils;
import org.dcais.stock.stock.dao.xdriver.fin.XFinIncomeDao;
import org.dcais.stock.stock.entity.info.FinIncome;
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
    for (FinIncome finIncome : finIncomes){
      Date endDate = DateUtils.smartFormat(finIncome.getEndDate());
      Calendar cal = Calendar.getInstance();
      cal.setTime(endDate);
      int month = cal.get(Calendar.MONTH);
      if(month == 2){
        finIncome.setReportSeason(1);
      }else if (month == 5) {
        finIncome.setReportSeason(2);
      } else if (month == 8) {
        finIncome.setReportSeason(3);
      } else if (month == 11) {
        finIncome.setReportSeason(4);
      }
      int year = cal.get(Calendar.YEAR);
      finIncome.setReportYear(year);
    }

    xFinIncomeDao.insertList(finIncomes);
    return Result.wrapSuccessfulResult("");
  }
}
