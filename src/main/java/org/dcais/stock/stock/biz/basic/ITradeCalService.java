package org.dcais.stock.stock.biz.basic;


import org.dcais.stock.stock.biz.IBaseService;
import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.entity.basic.TradeCal;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 交易日历 服务类
 * </p>
 *
 * @author david-cai
 * @since 2020-05-24
 */
public interface ITradeCalService extends IBaseService<TradeCal> {
  Result sync();

  LocalDateTime getLastTradeDate();
}
