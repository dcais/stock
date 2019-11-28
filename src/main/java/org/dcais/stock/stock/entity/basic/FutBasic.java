package org.dcais.stock.stock.entity.basic;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FutBasic {
  private String tsCode; //合约代码
  private String symbol; //交易标识
  private String exchange; //交易市场
  private String name; //中文简称
  private String futCode; //合约产品代码
  private BigDecimal multiplier; //合约乘数
  private String tradeUnit; //交易计量单位
  private BigDecimal perUnit; //交易单位(每手)
  private String quoteUnit; //报价单位
  private String quoteUnitDesc; //最小报价单位说明
  private String dModeDesc; //交割方式说明
  private String BigDecimal; //上市日期
  private String delistDate; //最后交易日期
  private String dMonth; //交割月份
  private String lastDdate; //最后交割日
  private String tradeTimeDesc; //交易时间说明

}
