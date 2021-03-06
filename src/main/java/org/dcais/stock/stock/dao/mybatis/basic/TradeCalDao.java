package org.dcais.stock.stock.dao.mybatis.basic;


import org.apache.ibatis.annotations.Mapper;
import org.dcais.stock.stock.dao.mybatis.BaseDao;
import org.dcais.stock.stock.entity.basic.TradeCal;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface TradeCalDao extends BaseDao<TradeCal> {

  List<TradeCal> select(Map<String, Object> params);

  Integer selectCount(Map<String, Object> params);

  Date selectMaxCalDate(String exchange);

  List<TradeCal> getLastTradeDate(String exchange);
}
