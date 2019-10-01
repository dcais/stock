package org.dcais.stock.stock.dao.xdriver.common;

import lombok.Data;

@Data
public class CollectionIndexDef {
  // field: string, the full document path to the document member or field to be indexed</li>
  private String field;
  //type: string, one of the supported SQL column types to map the field into (see below for a list). For numeric types, the optional UNSIGNED
  //keyword may follow. For the TEXT type, the length to consider for indexing may be added. Type descriptions are case insensitive
  private String type;
  //required: bool, (optional) true if the field is required to exist in the document. Defaults to false, except for GEOJSON where it defaults
  // to true</li>
  private boolean required;
  //options: int, (optional) special option flags for use when decoding GEOJSON data</li>
//  private Integer option;
  // srid: int, (optional) srid value for use when decoding GEOJSON data
//  private Integer srid;

  public CollectionIndexDef(String field,String type){
    this.field = field;
    this.type = type;
  }
}
