package org.dcais.stock.stock.biz;

import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.info.IDailyBasicService;
import org.dcais.stock.stock.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;


@ActiveProfiles("dev")
@SpringBootTest
@Slf4j
public class DailyBasicServiceTests extends AbstractTestNGSpringContextTests {

  @Autowired
  private IDailyBasicService dailyBasicService;

  @Test(enabled = true)
  public void syncDailyBasic() {
    Result r = dailyBasicService.syncHistory("000001");
  }


}
