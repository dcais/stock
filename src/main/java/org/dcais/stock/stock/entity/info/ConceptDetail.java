package org.dcais.stock.stock.entity.info;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.dcais.stock.stock.entity.BaseEntity;

@EqualsAndHashCode(callSuper = false)
@Data
@Accessors(chain = true)
@TableName("stock_concept_detail")
public class ConceptDetail  extends BaseEntity {

  /**
   * 股票代码
   */
  private String tsCode;

  /**
   * 股票名称
   */
  private String name;

  /**
   * 概念分类ID
   */
  private String conceptCode;

  /**
   * 概念分类名称
   */
  private String conceptName;

}
