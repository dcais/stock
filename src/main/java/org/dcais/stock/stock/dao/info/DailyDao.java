package org.dcais.stock.stock.dao.info;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.dcais.stock.stock.dao.BaseDao;
import org.dcais.stock.stock.entity.info.Daily;

import java.util.List;
import java.util.Map;

@Mapper
public interface DailyDao extends BaseDao<Daily> {

    List<Daily> select(Map<String, Object> params);

    Integer selectCount(Map<String, Object> params);

    List<Daily> getMaxDaily(String tsCode);

    Integer batchInsert(@Param("list")List<Daily> dailies);
}
