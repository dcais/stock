package org.dcais.stock.stock.entity.basic;


import java.time.LocalDateTime;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import lombok.experimental.Accessors;
import org.dcais.stock.stock.entity.BaseEntity;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@TableName("stock_trade_cal")
public class TradeCal extends BaseEntity {


  /**
   * 交易所 SSE上交所 SZSE深交所
   */
  private String exchange;

  /**
   * 日历日期
   */
  private LocalDateTime calDate;

  /**
   * 是否交易 0休市 1交易
   */
  private Integer isOpen;

  /**
   * 上一个交易日
   */
  private LocalDateTime pretradeDate;


}
