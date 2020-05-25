package org.dcais.stock.stock.biz.info;

import org.dcais.stock.stock.biz.IBaseService;
import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.entity.info.SplitAdjustedDaily;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 前复权日线行情 服务类
 * </p>
 *
 * @author david-cai
 * @since 2020-05-25
 */
public interface ISplitAdjustedDailyService extends IBaseService<SplitAdjustedDaily> {
  Result calcSplitAdjust(String tsCode);

  Result calcSplitAdjust(String tsCode, boolean forceRemove);

  Result<List<SplitAdjustedDaily>> getSplitAdjustDailyList(String tsCode, Date gteDate, Date lteDate);
}
