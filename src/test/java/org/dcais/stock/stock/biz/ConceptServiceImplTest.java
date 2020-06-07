package org.dcais.stock.stock.biz;

import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.info.IConceptService;
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
  private IConceptService conceptService;

  @Test
  public void test() {conceptService.sync();}

  @Test
  public void getAll(){
    List<Concept> conceptList =  conceptService.getAll();
    for(Concept concept: conceptList){
      log.info(String.format("%s:%s", concept.getCode(), concept.getName()));
    }
  }
}
