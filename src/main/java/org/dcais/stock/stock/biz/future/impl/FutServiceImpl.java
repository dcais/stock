package org.dcais.stock.stock.biz.future.impl;

import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.future.FutExchangeService;
import org.dcais.stock.stock.biz.future.FutService;
import org.dcais.stock.stock.biz.tushare.FutureInfoService;
import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.dao.xdriver.fut.XFutBasicDao;
import org.dcais.stock.stock.entity.basic.FutBasic;
import org.dcais.stock.stock.entity.basic.FutExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@Service
@Slf4j
public class FutServiceImpl implements FutService {
  @Autowired
  private XFutBasicDao xFutBasicDao;
  @Autowired
  private FutExchangeService futExchangeService;

  @Autowired
  private FutureInfoService futureInfoService;

  @Override
  public void syncBasic() {
    List<FutExchange> exchanges = futExchangeService.getAll();
    for(FutExchange futExchange: exchanges){
      List<FutBasic> inDbFutBasics= xFutBasicDao.getByExange(futExchange.getCode());
      Map<String,FutBasic> map = inDbFutBasics.stream().collect(Collectors.toMap(
        FutBasic::getTsCode
        , Function.identity()
        , (oldV,newV) -> newV));

      Result rFutBasics = futureInfoService.futBasic(futExchange.getCode());
      if(!rFutBasics.isSuccess() ){
        continue;
      }
      List<FutBasic> futBasics = (List<FutBasic>) rFutBasics.getData();
      List<FutBasic> toSave = new LinkedList<>();
      for(FutBasic futBasic: futBasics){
        if( map.get(futBasic.getTsCode()) == null){
          toSave.add(futBasic);
        }
      }
      if(toSave.size() == 0 ){
        continue;
      }
      xFutBasicDao.insertList(toSave);
    }
  }

  public void syncDaily(){

  }


}
