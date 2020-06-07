package org.dcais.stock.stock.biz.info.impl;

import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.IBaseServiceImpl;
import org.dcais.stock.stock.biz.info.IConceptDetailService;
import org.dcais.stock.stock.biz.info.IConceptService;
import org.dcais.stock.stock.biz.tushare.ConceptInfoService;
import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.dao.mybatisplus.info.ConceptDetailMapper;
import org.dcais.stock.stock.entity.info.Concept;
import org.dcais.stock.stock.entity.info.ConceptDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ConceptDetailServiceImpl extends IBaseServiceImpl<ConceptDetailMapper, ConceptDetail> implements IConceptDetailService {
  @Autowired
  private ConceptDetailMapper conceptDetailMapper;
  @Autowired
  private ConceptInfoService conceptInfoService;
  @Autowired
  private IConceptService conceptService;

  @Override
  public void sync(){
    List<Concept> concepts = conceptService.getAll();
    conceptDetailMapper.trueDeleteAll();
    for(Concept concept:concepts){
      Result r = conceptInfoService.conceptDetail(concept.getCode());
      if(!r.isSuccess()){
        log.error(r.getErrorMsg());
        return;
      }
      this.saveBatch((List<ConceptDetail>) r.getData());
    }
  }

}
