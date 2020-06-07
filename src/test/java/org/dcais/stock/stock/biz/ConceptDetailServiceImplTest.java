package org.dcais.stock.stock.biz;

import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.info.impl.ConceptDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;


@ActiveProfiles("dev")
@SpringBootTest
@Slf4j
public class ConceptDetailServiceImplTest extends AbstractTestNGSpringContextTests {
  @Autowired
  private ConceptDetailServiceImpl conceptDetailServiceImpl;

  @Test
  public void test() {conceptDetailServiceImpl.sync();}

}
