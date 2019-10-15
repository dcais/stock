package org.dcais.stock.stock.entity.info;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dcais.stock.stock.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

@EqualsAndHashCode(callSuper = false)
@Data
public class Concept extends BaseEntity {

  private String code;
  private String name;
  private String src;

}
