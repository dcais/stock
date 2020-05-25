package org.dcais.stock.stock.biz.info;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

@ActiveProfiles("dev")
@SpringBootTest
@Slf4j
public class SpliteAdjustedDailyServiceTest extends AbstractTestNGSpringContextTests {
  @Autowired
  private ISplitAdjustedDailyService splitAdjustedDailyService;

  @Test
  public void testCalcSplitAdjustDaily(){
    splitAdjustedDailyService.calcSplitAdjust("000001.SZ", true);
  }
}
