package org.dcais.stock.stock.dao.mybatisplus.info;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.dcais.stock.stock.entity.info.ConceptDetail;

/**
 * <p>
 * 概念股分类详情 Mapper 接口
 * </p>
 *
 * @author david-cai
 * @since 2020-06-07
 */
public interface ConceptDetailMapper extends BaseMapper<ConceptDetail> {
  int trueDeleteAll();

}
