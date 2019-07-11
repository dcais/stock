package org.dcais.stock.stock.entity.basic;

import java.util.Date;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dcais.stock.stock.entity.BaseEntity;


@EqualsAndHashCode(callSuper = false)
@Data
public class Basic extends BaseEntity {

  private String tsCode;
  private String symbol;
  private String name;
  private String area;
  private String industry;
  private String fullname;
  private String enname;
  private String market;
  private String exchange;
  private String currType;
  private String listStatus;
  private Date listDate;
  private Date delistDate;
  private String isHs;

}
