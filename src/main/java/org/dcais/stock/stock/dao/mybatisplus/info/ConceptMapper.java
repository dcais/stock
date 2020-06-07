package org.dcais.stock.stock.dao.mybatisplus.info;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.dcais.stock.stock.entity.info.Concept;

/**
 * <p>
 * 概念股分类 Mapper 接口
 * </p>
 *
 * @author david-cai
 * @since 2020-06-07
 */
public interface ConceptMapper extends BaseMapper<Concept> {
  int trueDeleteAll();

}
