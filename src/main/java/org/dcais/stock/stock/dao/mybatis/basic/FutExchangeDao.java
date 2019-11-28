package org.dcais.stock.stock.dao.mybatis.basic;

import org.apache.ibatis.annotations.Mapper;
import org.dcais.stock.stock.dao.mybatis.BaseDao;
import org.dcais.stock.stock.entity.basic.Basic;
import org.dcais.stock.stock.entity.basic.FutExchange;

import java.util.List;
import java.util.Map;

@Mapper
public interface FutExchangeDao extends BaseDao<FutExchange> {

  List<FutExchange> select(Map<String, Object> params);

  Integer selectCount(Map<String, Object> params);
}
