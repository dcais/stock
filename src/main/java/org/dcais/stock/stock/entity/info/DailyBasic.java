package org.dcais.stock.stock.entity.info;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dcais.stock.stock.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

@EqualsAndHashCode(callSuper = false)
@Data
public class DailyBasic extends BaseEntity {

  private String tsCode;
  private Date tradeDate;
  private BigDecimal close;
  //换手率（%）
  private Double turnoverRate;
  //换手率（自由流通股）
  private Double turnoverRateF;
  //量比
  private Double volumeRatio;
  //市盈率（总市值/净利润）
  private Double pe;
  //市盈率（TTM）
  private Double peTtm;
  //市净率（总市值/净资产）
  private Double pb;
  // 市销率
  private Double ps;
  // 市销率（TTM）
  private Double psTtm;
  // 总股本 （万股）
  private Double totalShare;
  // 流通股本 （万股）
  private Double floatShare;
  // 自由流通股本 （万）
  private Double freeShare;
  // 总市值 （万元）
  private Double totalMv;
  // 流通市值（万元）
  private Double circMv;
}
