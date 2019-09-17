package org.dcais.stock.stock.biz.ana;

import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.tec.MAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.Arrays;

@ActiveProfiles("dev")
@SpringBootTest
@Slf4j
@Test
public class AnaTagServiceTest extends AbstractTestNGSpringContextTests {
  @Autowired
  private AnaTagService anaTagService;

  @Test
  public void test(){
    anaTagService.ana("000001.SZ");

  }


}
