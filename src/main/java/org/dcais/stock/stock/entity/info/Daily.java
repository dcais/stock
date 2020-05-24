package org.dcais.stock.stock.entity.info;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.dcais.stock.stock.entity.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 日线行情
 * </p>
 *
 * @author david-cai
 * @since 2020-05-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("stock_daily")
public class Daily extends BaseEntity {


  /**
   * 股票代码
   */
  private String tsCode;

  /**
   * 交易日期
   */
  private LocalDateTime tradeDate;

  /**
   * 开盘价
   */
  private BigDecimal open;

  /**
   * 最高价
   */
  private BigDecimal high;

  /**
   * 最低价
   */
  private BigDecimal low;

  /**
   * 收盘价
   */
  private BigDecimal close;

  /**
   * 昨收价
   */
  private BigDecimal preClose;

  /**
   * 涨跌额
   */
  private BigDecimal pctChg;

  /**
   * 成交量 （手）
   */
  private Integer vol;

  /**
   * 成交额 （千元）
   */
  private BigDecimal amount;


}
