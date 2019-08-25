package org.dcais.stock.stock.dao.mybatis.info;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.dcais.stock.stock.dao.mybatis.BaseDao;
import org.dcais.stock.stock.entity.info.AdjFactor;

import java.util.List;
import java.util.Map;

@Mapper
public interface AdjFactorDao extends BaseDao<AdjFactor> {

    List<AdjFactor> select(Map<String, Object> params);

    Integer selectCount(Map<String, Object> params);

    List<AdjFactor> getMaxDaily(String tsCode);

    Integer batchInsert(@Param("list")List<AdjFactor> dailies);
}
