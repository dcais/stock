package org.dcais.stock.stock.http.tushare.result;

import lombok.Data;

import java.util.List;

@Data
public class TushareData {
  private List<String> fields;
  private List<List<Object>> items;
}
