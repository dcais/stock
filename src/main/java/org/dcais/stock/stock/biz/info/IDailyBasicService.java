package org.dcais.stock.stock.biz.info;

import org.dcais.stock.stock.biz.IBaseService;
import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.entity.info.DailyBasic;

/**
 * <p>
 * 每日指标 服务类
 * </p>
 *
 * @author david-cai
 * @since 2020-05-25
 */
public interface IDailyBasicService extends IBaseService<DailyBasic> {
  void syncAll(String mode);

  Result syncHistory(String symbol);
}
