package org.dcais.stock.stock;

import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.tushare.FinInfoService;
import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.dao.xdriver.meta.XStockMetaDao;
import org.dcais.stock.stock.entity.info.FinIncome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.List;


@ActiveProfiles("dev")
@SpringBootTest
@Slf4j
public class FinInfoTests extends AbstractTestNGSpringContextTests {

  @Autowired
  private FinInfoService finInfoService;

  @Test(enabled = true)
  public void getFinInfo() {
    Result r = finInfoService.finIncome("000001.SZ");
    List<FinIncome> finIncomeList = (List<FinIncome>) r.getData();
  }


}
