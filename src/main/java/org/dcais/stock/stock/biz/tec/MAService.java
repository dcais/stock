package org.dcais.stock.stock.biz.tec;

import java.util.List;

public interface MAService {
  void calcSMA(String tsCode, List<Integer> periods);
}
