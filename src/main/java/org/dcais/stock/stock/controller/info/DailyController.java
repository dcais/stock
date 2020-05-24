package org.dcais.stock.stock.controller.info;

import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.info.IDailyService;
import org.dcais.stock.stock.biz.info.SplitAdjustService;
import org.dcais.stock.stock.common.cons.CmnConstants;
import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.common.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("daily")
@Slf4j
@RestController
public class DailyController {
  @Autowired
  private IDailyService dailyService;
  @Autowired
  private SplitAdjustService splitAdjustService;

  @RequestMapping(value = "/syncBySymbol", method = RequestMethod.GET)
  @ResponseBody
  public Result syncBySymbol(@RequestParam(required = true) String symbol) {
    return dailyService.syncHistory(symbol);
  }

  @RequestMapping(value = "/syncAll", method = RequestMethod.GET)
  @ResponseBody
  public Result syncAll(String mode) {
    if (StringUtil.isBlank(mode)) {
      mode = CmnConstants.SYNC_MODE_DATE;
    }
    dailyService.syncAll(mode);
    return Result.wrapSuccessfulResult("OK");
  }

  @RequestMapping(value = "/calcSplitAdjust", method = RequestMethod.GET)
  @ResponseBody
  public Result calcSplitAdjust(String tsCode,@RequestParam(defaultValue = "N") String isForce ){
    return splitAdjustService.calcSplitAdjust(tsCode,"Y".equalsIgnoreCase(isForce));
  }
}
