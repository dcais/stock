package org.dcais.stock.stock.task;

import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.info.ISplitAdjustedDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

@ActiveProfiles("dev")
@SpringBootTest
@Slf4j
public class SplitAdjustTaskTest extends AbstractTestNGSpringContextTests {
  @Autowired
  SplitAdjustTask splitAdjustTask;
  @Autowired
  private ISplitAdjustedDailyService splitAdjustedDailyService;

  @Test
  public void single(){
    splitAdjustedDailyService.calcSplitAdjust("600609.SH");
  }
  @Test(enabled = false)
  public void run(){
    splitAdjustTask.startCalc();
  }

}
