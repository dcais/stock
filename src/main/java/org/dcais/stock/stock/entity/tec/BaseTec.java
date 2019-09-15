package org.dcais.stock.stock.entity.tec;


import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class BaseTec {
  Date tradeDate;
  String tsCode;
  String tecName;
  BigDecimal value;
}
