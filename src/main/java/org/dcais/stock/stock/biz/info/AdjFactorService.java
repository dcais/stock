package org.dcais.stock.stock.biz.info;


import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.entity.info.AdjFactor;

import java.util.List;
import java.util.Map;


public interface AdjFactorService {
  List<AdjFactor> getAll();

  List<AdjFactor> select(Map<String, Object> param);

  Integer selectCount(Map<String, Object> param);

  AdjFactor getById(Long id);

  boolean save(AdjFactor adjFactor);

  boolean deleteById(Long id);

  int deleteByIds(Long[] ids);


  void syncAll(String mode);

  Result syncHistory(String symbol);
}
