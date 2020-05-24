package org.dcais.stock.stock.biz.cal;

import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.basic.ITradeCalService;
import org.dcais.stock.stock.common.utils.DateUtils;
import org.dcais.stock.stock.common.utils.LocalDateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.time.LocalDateTime;

@ActiveProfiles("dev")
@SpringBootTest
@Slf4j
@Test
public class TradeCalServiceTest extends AbstractTestNGSpringContextTests {
  @Autowired
  private ITradeCalService tradeCalService;
  @Test(priority = 0)
  public void syncTest(){
    tradeCalService.sync();
  }

  @Test(priority = 1)
  public void getLastTest(){
    LocalDateTime date =tradeCalService.getLastTradeDate();
    log.info(""+ LocalDateUtils.formatToStr(date,LocalDateUtils.Y_M_D_HMSS));
    log.info(""+ LocalDateUtils.formatToStr(date,LocalDateUtils.ISO_DATE_TIME));

  }


}
