package org.dcais.stock.stock.biz.info;

import org.dcais.stock.stock.biz.IBaseService;
import org.dcais.stock.stock.entity.info.ConceptDetail;

/**
 * <p>
 * 概念股分类详情 服务类
 * </p>
 *
 * @author david-cai
 * @since 2020-06-07
 */
public interface IConceptDetailService extends IBaseService<ConceptDetail> {
  void sync();
}
