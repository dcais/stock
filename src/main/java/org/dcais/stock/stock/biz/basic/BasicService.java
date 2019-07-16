package org.dcais.stock.stock.biz.basic;

import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.entity.basic.Basic;
import java.util.List;
import java.util.Map;


public interface BasicService {
  List<Basic> getAll();

  List<Basic> select(Map<String,Object> params);

  Integer selectCount(Map<String,Object>  params);

  Basic getById(Long id);

  boolean save(Basic basic);

  boolean deleteById( Long id);

  int deleteByIds(Long[] ids);

  Result sync();

  Basic getBySymbol(String symbol);

  List<Basic> getAllList();
}
