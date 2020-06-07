package org.dcais.stock.stock.biz.tushare.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class TushareConceptDetail {

  private String id;
  private String tsCode;
  private String name;
  private String conceptName;

}
