package org.dcais.stock.stock.entity.basic;

import java.time.LocalDateTime;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.dcais.stock.stock.annotation.TuShareField;
import org.dcais.stock.stock.entity.BaseEntity;
/**
 * <p>
 * 股票基本信息
 * </p>
 *
 * @author david-cai
 * @since 2020-05-23
 */

@Accessors(chain = true)
@TableName("stock_basic")
@EqualsAndHashCode(callSuper = false)
@Data
public class Basic extends BaseEntity {

  /**
   * TS代码
   */
  @TuShareField("ts_code")
  private String tsCode;

  /**
   * 股票代码
   */
  private String symbol;

  /**
   * 股票名称
   */
  private String name;

  /**
   * 所在地域
   */
  private String area;

  /**
   * 所在行业
   */
  private String industry;

  /**
   * 股票全称
   */
  private String fullname;

  /**
   * 英文全称
   */
  private String enname;

  /**
   * 市场类型
   */
  private String market;

  /**
   * 交易所代码 SSE上交所 SZSE深交所 HKEX港交所(未上线)
   */
  private String exchange;

  /**
   * 交易货币
   */
  private String currType;

  /**
   * 上市状态  L上市 D退市 P暂停上市
   */
  private String listStatus;

  /**
   * 上市日期
   */
  private LocalDateTime listDate;

  /**
   * 退市日期
   */
  private LocalDateTime delistDate;

  /**
   * 是否沪深港通标的，N否 H沪股通 S深股通
   */
  private String isHs;
}




