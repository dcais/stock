package org.dcais.stock.stock.dao.xdriver.common;

import lombok.Data;

import java.util.List;

@Data
public class CollectionIndexInfo {
  private String indexName;
  private List<CollectionIndexDef> fields;
  private String type;


}
