package org.dcais.stock.stock.biz.info;


import org.dcais.stock.stock.biz.IBaseService;
import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.entity.info.Daily;

/**
 * <p>
 * 日线行情 服务类
 * </p>
 *
 * @author david-cai
 * @since 2020-05-24
 */
public interface IDailyService extends IBaseService<Daily> {
  void syncAll(String mode);

  Result syncHistory(String symbol);
}
