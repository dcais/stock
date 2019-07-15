package org.dcais.stock.stock.entity.basic;


import java.util.Date;


import lombok.Data;
import lombok.EqualsAndHashCode;

import org.dcais.stock.stock.entity.BaseEntity;

@EqualsAndHashCode(callSuper = false)
@Data
public class TradeCal extends BaseEntity {

  private String exchange;
  private Date calDate;
  private Integer isOpen;
  private Date pretradeDate;

}
