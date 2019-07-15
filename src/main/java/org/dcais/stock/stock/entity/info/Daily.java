package org.dcais.stock.stock.entity.info;


import java.util.Date;
import java.math.BigDecimal;


import lombok.Data;
import lombok.EqualsAndHashCode;

import org.dcais.stock.stock.entity.BaseEntity;

@EqualsAndHashCode(callSuper = false)
@Data
public class Daily extends BaseEntity {

  private String tsCode;
  private Date tradeDate;
  private BigDecimal open;
  private BigDecimal high;
  private BigDecimal low;
  private BigDecimal close;
  private BigDecimal preClose;
  private BigDecimal pctChg;
  private Long vol;
  private BigDecimal amount;

}
