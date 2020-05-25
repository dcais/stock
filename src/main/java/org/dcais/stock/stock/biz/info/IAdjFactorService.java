package org.dcais.stock.stock.biz.info;

import org.dcais.stock.stock.biz.IBaseService;
import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.entity.info.AdjFactor;

/**
 * <p>
 * 复权因子 服务类
 * </p>
 *
 * @author david-cai
 * @since 2020-05-25
 */
public interface IAdjFactorService extends IBaseService<AdjFactor> {
  void syncAll(String mode);

  Result syncHistory(String symbol);

  Result<AdjFactor> getMaxDaily(String tsCode);
}
