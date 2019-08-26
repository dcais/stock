package org.dcais.stock.stock.entity.info;


import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class SplitAdjustedDaily extends Daily{
  String _id;
}
