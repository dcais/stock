package org.dcais.stock.stock.biz.info.impl;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.BaseServiceImpl;
import org.dcais.stock.stock.biz.info.ConceptService;
import org.dcais.stock.stock.biz.tushare.ConceptInfoService;
import org.dcais.stock.stock.biz.tushare.StockInfoService;
import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.dao.xdriver.concept.XConceptDao;
import org.dcais.stock.stock.entity.info.Concept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ConceptServiceImpl implements ConceptService {
  @Autowired
  private XConceptDao xConceptDao;
  @Autowired
  private ConceptInfoService conceptInfoService;

  @Override
  public void sync(){
    Result r = conceptInfoService.concept();
    if(!r.isSuccess()){
      log.error(r.getErrorMsg());
      return;
    }
    xConceptDao.deleteAll();
    xConceptDao.insertList((List<Concept>) r.getData());
  }

  @Override
  public List<Concept> getAll(){
    return xConceptDao.getAll();
  }

}
