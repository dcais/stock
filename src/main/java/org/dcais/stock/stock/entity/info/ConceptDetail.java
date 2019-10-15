package org.dcais.stock.stock.entity.info;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dcais.stock.stock.entity.BaseEntity;

@EqualsAndHashCode(callSuper = false)
@Data
public class ConceptDetail {

  private String id;
  private String code;
  private String tsCode;
  private String name;
  private String conceptName;

}
