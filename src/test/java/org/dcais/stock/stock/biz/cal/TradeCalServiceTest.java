package org.dcais.stock.stock.biz.cal;

import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.basic.TradeCalService;
import org.dcais.stock.stock.biz.tec.MAService;
import org.dcais.stock.stock.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Date;

@ActiveProfiles("dev")
@SpringBootTest
@Slf4j
@Test
public class TradeCalServiceTest extends AbstractTestNGSpringContextTests {
  @Autowired
  private TradeCalService tradeCalService;

  @Test
  public void test(){
    Date date =tradeCalService.getLastTradeDate();
    log.info(""+ DateUtils.formatDate(date,DateUtils.ISO_DATE_TIME));

  }


}
