package org.dcais.stock.stock.dao.mybatisplus.info;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.dcais.stock.stock.entity.info.DailyBasic;

import java.util.Date;

/**
 * <p>
 * 每日指标 Mapper 接口
 * </p>
 *
 * @author david-cai
 * @since 2020-05-25
 */
public interface DailyBasicMapper extends BaseMapper<DailyBasic> {
  int deleteDateAfterDate(@Param("tradeDate") Date tradeDate);
}
