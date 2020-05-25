package org.dcais.stock.stock.entity.info;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.dcais.stock.stock.entity.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("stock_daily_basic")
public class DailyBasic extends BaseEntity {


  /**
   * ts code
   */
  private String tsCode;

  /**
   * 交易日期
   */
  private LocalDateTime tradeDate;

  /**
   * 收盘价
   */
  private BigDecimal close;

  /**
   * 换手率
   */
  private BigDecimal turnoverRate;

  /**
   * 换手率(自由流通股)
   */
  private BigDecimal turnoverRateF;

  /**
   * 量比
   */
  private BigDecimal volumeRatio;

  /**
   * 市盈率(总市值/净利润)
   */
  private BigDecimal pe;

  /**
   * 市盈率TTM
   */
  private BigDecimal peTtm;

  /**
   * 市净率(总市值/净资产)
   */
  private BigDecimal pb;

  /**
   * 市销率
   */
  private BigDecimal ps;

  /**
   * 市销率（TTM）
   */
  private BigDecimal psTtm;

  /**
   * 总股本 （万股）
   */
  private BigDecimal totalShare;

  /**
   * 流通股本 （万股）
   */
  private BigDecimal floatShare;

  /**
   * 自由流通股本 （万）
   */
  private BigDecimal freeShare;

  /**
   * 自由流通股本 （万）
   */
  private BigDecimal totalMv;

  /**
   * 流通市值（万元）
   */
  private BigDecimal circMv;


}
