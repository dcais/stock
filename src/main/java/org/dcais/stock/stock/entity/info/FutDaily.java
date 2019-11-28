package org.dcais.stock.stock.entity.info;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class FutDaily {
  private String tsCode; //TS合约代码
  private Date tradeDate; //交易日期
  private BigDecimal preClose; //昨收盘价
  private BigDecimal preSettle; //昨结算价
  private BigDecimal open; //开盘价
  private BigDecimal high; //最高价
  private BigDecimal low; //最低价
  private BigDecimal close; //收盘价
  private BigDecimal settle; //结算价
  private BigDecimal change1; //涨跌1 收盘价-昨结算价
  private BigDecimal change2; //涨跌2 结算价-昨结算价
  private BigDecimal vol; //成交量(手)
  private BigDecimal amount; //成交金额(万元)
  private BigDecimal oi; //持仓量(手)
  private BigDecimal oiChg; //持仓量变化
  private BigDecimal delvSettle; //交割结算价
}
