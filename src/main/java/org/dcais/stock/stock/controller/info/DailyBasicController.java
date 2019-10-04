package org.dcais.stock.stock.controller.info;

import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.info.DailyBasicService;
import org.dcais.stock.stock.common.cons.CmnConstants;
import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.common.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RequestMapping("dailyBasic")
@Slf4j
@RestController
public class DailyBasicController {
  @Autowired
  private DailyBasicService dailyBasicService;

  @RequestMapping(value = "/syncBySymbol", method = RequestMethod.GET)
  @ResponseBody
  public Result syncBySymbol(@RequestParam(required = true) String symbol) {
    return dailyBasicService.syncHistory(symbol);
  }

  @RequestMapping(value = "/syncAll", method = RequestMethod.GET)
  @ResponseBody
  public Result syncAll(String mode) {
    if (StringUtil.isBlank(mode)) {
      mode = CmnConstants.SYNC_MODE_DATE;
    }
    dailyBasicService.syncAll(mode);
    return Result.wrapSuccessfulResult("OK");
  }

}
