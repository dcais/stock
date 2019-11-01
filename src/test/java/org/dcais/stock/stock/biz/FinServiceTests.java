package org.dcais.stock.stock.biz;

import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.info.FinService;
import org.dcais.stock.stock.biz.tushare.FinInfoService;
import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.entity.info.FinIncome;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.List;


@ActiveProfiles("dev")
@SpringBootTest
@Slf4j
public class FinServiceTests extends AbstractTestNGSpringContextTests {

  @Autowired
  private FinService finService;

  @Test(enabled = false)
  public void syncFinIncome() {
    Result r = finService.syncFinIncome("000001.SZ");
  }

  @Test(enabled = false)
  public void syncFinIndicator() {
    Result r = finService.syncFinIndicator("000001.SZ");
  }
  @Test(enabled = false)
  public void calcFinIndicatorRate(){
    Result r = finService.calcFinIndicatorRate(1999,1);
  }

  @Test(enabled = true)
  public void getCANSLIMCandidates(){
    Result r = finService.getCANSLIMCandidates(2019,3);
  }

}
