package org.dcais.stock.stock.biz.tec;

import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.tec.impl.MAServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.Arrays;

@ActiveProfiles("dev")
@SpringBootTest
@Slf4j
public class MAServiceTest extends AbstractTestNGSpringContextTests {
  @Autowired
  private MAServiceImpl maServiceImpl;

  @Test
  public void test() throws Exception {
    maServiceImpl.calcSMA("000001.SZ", Arrays.asList(8, 17, 25, 99, 145, 318, 453, 732, 890));
  }


}
