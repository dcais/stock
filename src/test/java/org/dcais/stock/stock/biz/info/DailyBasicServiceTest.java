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
public class DailyBasicServiceTest extends AbstractTestNGSpringContextTests {
  @Autowired
  private IDailyBasicService dailyBasicService;

  @Test
  public void testSyncHistory(){
    dailyBasicService.syncHistory("000001");

  }
}
