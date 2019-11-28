package org.dcais.stock.stock.entity.basic;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dcais.stock.stock.entity.BaseEntity;

@EqualsAndHashCode(callSuper = false)
@Data
public class FutExchange extends BaseEntity
{
  private String code;
  private String name;
}
