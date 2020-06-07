package org.dcais.stock.stock.biz.info;

import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.common.cons.CmnConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

@ActiveProfiles("dev")
@SpringBootTest
@Slf4j
public class DailyServiceTest extends AbstractTestNGSpringContextTests {
  @Autowired
  private IDailyService dailyService;

  @Test
  public void testSyncHistory(){
    dailyService.syncHistory("000001");

  }

  @Test
  public void testAll(){
    dailyService.syncAll(CmnConstants.SYNC_MODE_SYMBOL);

  }
}
