package org.dcais.stock.stock.dao.mybatis.basic;

import org.apache.ibatis.annotations.Mapper;
import org.dcais.stock.stock.dao.mybatis.BaseDao;
import org.dcais.stock.stock.entity.basic.Basic;

import java.util.List;
import java.util.Map;

@Mapper
public interface BasicDao extends BaseDao<Basic> {

  public List<Basic> select(Map<String, Object> params);

  public Integer selectCount(Map<String, Object> params);
}
