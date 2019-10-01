package org.dcais.stock.stock.biz.info.impl;

import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.info.FinService;
import org.dcais.stock.stock.biz.tushare.FinInfoService;
import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.dao.xdriver.fin.XFinIncomeDao;
import org.dcais.stock.stock.entity.info.FinIncome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
