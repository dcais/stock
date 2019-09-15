package org.dcais.stock.stock.biz.info;

import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.entity.info.SplitAdjustedDaily;

import java.util.Date;
import java.util.List;

public interface SplitAdjustService {
  Result calcSplitAdjust(String tsCode);
  Result<List<SplitAdjustedDaily>> getSplitAdjustDailyList(String tsCode, Date gteDate);
}
