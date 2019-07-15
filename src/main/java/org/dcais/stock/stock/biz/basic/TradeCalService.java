package org.dcais.stock.stock.biz.basic;


import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.entity.basic.TradeCal;

import java.util.List;
import java.util.Map;


public interface TradeCalService {
  List<TradeCal> getAll();

  List<TradeCal> select(Map<String, Object> param);

  Integer selectCount(Map<String, Object> param);

  TradeCal getById(Long id);

  boolean save(TradeCal calDate);

  boolean deleteById(Long id);

  int deleteByIds(Long[] ids);


  Result sync();
}
