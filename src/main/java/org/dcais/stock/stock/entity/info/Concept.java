package org.dcais.stock.stock.entity.info;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.dcais.stock.stock.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("stock_concept")
public class Concept extends BaseEntity {


  /**
   * 来源
   */
  private String src;

  /**
   * 概念分类ID
   */
  private String code;

  /**
   * 概念分类名称
   */
  private String name;

}
