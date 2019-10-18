package org.dcais.stock.stock.biz.info.impl;

import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.info.ConceptDetailService;
import org.dcais.stock.stock.biz.info.ConceptService;
import org.dcais.stock.stock.biz.tushare.ConceptInfoService;
import org.dcais.stock.stock.biz.tushare.StockInfoService;
import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.dao.xdriver.concept.XConceptDao;
import org.dcais.stock.stock.dao.xdriver.concept.XConceptDetailDao;
import org.dcais.stock.stock.entity.info.Concept;
import org.dcais.stock.stock.entity.info.ConceptDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ConceptDetailServiceImpl implements ConceptDetailService {
  @Autowired
  private XConceptDetailDao xConceptDetailDao;
  @Autowired
  private ConceptInfoService conceptInfoService;
  @Autowired
  private ConceptService conceptService;

  @Override
  public void sync(){
    List<Concept> concepts = conceptService.getAll();
    xConceptDetailDao.deleteAll();
    for(Concept concept:concepts){
      Result r = conceptInfoService.conceptDetail(concept.getCode());
      if(!r.isSuccess()){
        log.error(r.getErrorMsg());
        return;
      }
      xConceptDetailDao.insertList((List<ConceptDetail>) r.getData());
    }
  }

}
