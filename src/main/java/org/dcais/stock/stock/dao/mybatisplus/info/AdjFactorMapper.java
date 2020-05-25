package org.dcais.stock.stock.dao.mybatisplus.info;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.dcais.stock.stock.entity.info.AdjFactor;

import java.util.Date;

/**
 * <p>
 * 复权因子 Mapper 接口
 * </p>
 *
 * @author david-cai
 * @since 2020-05-25
 */
public interface AdjFactorMapper extends BaseMapper<AdjFactor> {
  int deleteDateAfterDate(@Param("tradeDate") Date tradeDate);
}
