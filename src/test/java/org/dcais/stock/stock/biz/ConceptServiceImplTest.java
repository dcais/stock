package org.dcais.stock.stock.biz;

import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.info.ConceptService;
import org.dcais.stock.stock.biz.info.impl.ConceptServiceImpl;
import org.dcais.stock.stock.biz.info.impl.SplitAdjustServiceImpl;
import org.dcais.stock.stock.entity.info.Concept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.List;

@ActiveProfiles("dev")
@SpringBootTest
@Slf4j
public class ConceptServiceImplTest extends AbstractTestNGSpringContextTests {
  @Autowired
  private ConceptServiceImpl conceptServiceImpl;

  @Test
  public void test() {conceptServiceImpl.sync();}

  @Test
  public void getAll(){
    List<Concept> conceptList =  conceptServiceImpl.getAll();
    for(Concept concept: conceptList){
      log.info(String.format("%s:%s", concept.getCode(), concept.getName()));
    }
  }
}
