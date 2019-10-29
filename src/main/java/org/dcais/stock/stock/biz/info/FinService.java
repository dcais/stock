package org.dcais.stock.stock.biz.info;

import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.entity.info.SplitAdjustedDaily;

import java.util.Date;
import java.util.List;

public interface FinService {
  Result syncFinIncome(String tsCode);

  Result syncFinIndicator(String tsCode);

  Result calcFinIndicatorRate(int reportYear, int reportSeason);
}
