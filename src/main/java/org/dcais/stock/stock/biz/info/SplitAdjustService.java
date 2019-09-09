package org.dcais.stock.stock.biz.info;

import org.dcais.stock.stock.common.result.Result;

public interface SplitAdjustService {
  Result calcSplitAdjust(String tsCode);
}
