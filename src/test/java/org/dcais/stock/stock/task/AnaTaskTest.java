package org.dcais.stock.stock.task;

import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.info.SplitAdjustService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

@ActiveProfiles("prd")
@SpringBootTest
@Slf4j
public class AnaTaskTest extends AbstractTestNGSpringContextTests {
  @Autowired
  AnaTagTask anaTagTask;

  @Test
  public void test(){
    anaTagTask.startCalc(null);
  }

}
