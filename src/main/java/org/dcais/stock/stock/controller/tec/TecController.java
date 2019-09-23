package org.dcais.stock.stock.controller.tec;

import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.basic.BasicService;
import org.dcais.stock.stock.biz.basic.TradeCalService;
import org.dcais.stock.stock.biz.info.AdjFactorService;
import org.dcais.stock.stock.biz.info.DailyService;
import org.dcais.stock.stock.common.cons.CmnConstants;
import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.task.SMATask;
import org.dcais.stock.stock.task.SplitAdjustTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/tec")
public class TecController {

  @Autowired
  private SMATask smaTask;


  @RequestMapping(value = "/sma", method = RequestMethod.GET)
  @ResponseBody
  public Result sma(){
    smaTask.startCalc();
    return Result.wrapSuccessfulResult("OK");
  }

  @RequestMapping(value = "/sar", method = RequestMethod.GET)
  @ResponseBody
  public Result sar(){
    smaTask.startCalc();
    return Result.wrapSuccessfulResult("OK");
  }
}
