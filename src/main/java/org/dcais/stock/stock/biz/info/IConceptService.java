package org.dcais.stock.stock.biz.info;

import org.dcais.stock.stock.biz.IBaseService;
import org.dcais.stock.stock.entity.info.Concept;

import java.util.List;

/**
 * <p>
 * 概念股分类 服务类
 * </p>
 *
 * @author david-cai
 * @since 2020-06-07
 */
public interface IConceptService extends IBaseService<Concept> {
  void sync();

  List<Concept> getAll();
}
