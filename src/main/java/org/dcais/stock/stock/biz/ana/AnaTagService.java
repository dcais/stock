package org.dcais.stock.stock.biz.ana;

import java.util.Date;

public interface AnaTagService {
  void ana(String tsCode);

  void anaRS50(Date tradeDate);
}
