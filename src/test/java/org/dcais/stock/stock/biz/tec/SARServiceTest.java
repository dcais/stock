package org.dcais.stock.stock.biz.tec;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.Arrays;

@ActiveProfiles("dev")
@SpringBootTest
@Slf4j
public class SARServiceTest extends AbstractTestNGSpringContextTests {
  @Autowired
  private SARService sarService;

  @Test
  public void test(){
    sarService.calc("000001.SZ");

  }


}
