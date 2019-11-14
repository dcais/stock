package org.dcais.stock.stock.biz.ana;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface AnaTagService {
  Map ana(String tsCode);

  void anaCompare(Date tradeDate, List<Map> items);

  void anaRS50(Date tradeDate, List<Map> items);
}
