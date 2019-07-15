package org.dcais.stock.stock.biz.info;

import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.entity.info.Daily;

import java.util.List;
import java.util.Map;


public interface DailyService {
  List<Daily> getAll();

  List<Daily> select(Map<String, Object> param);

  Integer selectCount(Map<String, Object> param);

  Daily getById(Long id);

  boolean save(Daily daily);

  boolean deleteById(Long id);

  int deleteByIds(Long[] ids);

  Result syncHistory(String symbol);
}


