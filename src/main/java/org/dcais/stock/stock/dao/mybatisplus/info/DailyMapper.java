package org.dcais.stock.stock.dao.mybatisplus.info;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.dcais.stock.stock.entity.info.Daily;

import java.util.Date;

/**
 * <p>
 * 日线行情 Mapper 接口
 * </p>
 *
 * @author david-cai
 * @since 2020-05-24
 */
public interface DailyMapper extends BaseMapper<Daily> {
  int deleteDateAfterDate(@Param("tradeDate") Date tradeDate);
}
