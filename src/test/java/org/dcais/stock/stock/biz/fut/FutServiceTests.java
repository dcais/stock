package org.dcais.stock.stock.biz.fut;

import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.future.FutService;
import org.dcais.stock.stock.biz.info.FinService;
import org.dcais.stock.stock.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;


@ActiveProfiles("dev")
@SpringBootTest
@Slf4j
public class FutServiceTests extends AbstractTestNGSpringContextTests {

  @Autowired
  private FutService futService;


  @Test(enabled = true)
  public void syncBasic() {
    futService.syncBasic();
  }

}
