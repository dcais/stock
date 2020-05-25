package org.dcais.stock.stock.entity.info;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.dcais.stock.stock.entity.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = false)
@Data
@Accessors(chain = true)
@TableName("stock_adj_factor")
public class AdjFactor extends BaseEntity {

  /**
   * ts_code
   */
  private String tsCode;

  /**
   * 交易日期
   */
  private LocalDateTime tradeDate;

  /**
   * 复权因子
   */
  private BigDecimal adjFactor;


}
