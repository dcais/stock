package org.dcais.stock.stock.controller;

import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.basic.IBasicService;
import org.dcais.stock.stock.biz.basic.ITradeCalService;
import org.dcais.stock.stock.biz.info.*;
import org.dcais.stock.stock.common.cons.CmnConstants;
import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.task.AnaTagTask;
import org.dcais.stock.stock.task.SARTask;
import org.dcais.stock.stock.task.SMATask;
import org.dcais.stock.stock.task.SplitAdjustTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/")
public class StockController {
  @Autowired
  private IBasicService basicService;
  @Autowired
  private ITradeCalService tradeCalService;
  @Autowired
  private IDailyService dailyService;
  @Autowired
  private IAdjFactorService adjFactorService;
  @Autowired
  private SplitAdjustTask splitAdjustTask;
  @Autowired
  private SMATask smaTask;
  @Autowired
  private SARTask sarTask;
  @Autowired
  private AnaTagTask anaTagTask;
  @Autowired
  private DailyBasicService dailyBasicService;
  @Autowired
  private ConceptDetailService conceptDetailService;
  @Autowired
  private ConceptService conceptService;


  @RequestMapping(value = "/syncBasic", method = RequestMethod.GET)
  @ResponseBody
  public Result syncBasic() {
    return basicService.sync();
  }

  @RequestMapping(value = "/syncTradeCal", method = RequestMethod.GET)
  @ResponseBody
  public Result syncTradeCal() {
    return tradeCalService.sync();
  }


  @RequestMapping(value = "/logLevelTest", method = RequestMethod.GET)
  @ResponseBody
  public Result logLevelTest() {
    log.info("info");
    log.debug("debug");
    return Result.wrapSuccessfulResult("");
  }


  @RequestMapping(value = "/sync", method = RequestMethod.GET)
  @ResponseBody
  public Result sync(){
    basicService.sync();
    dailyService.syncAll(CmnConstants.SYNC_MODE_DATE);
    adjFactorService.syncAll(CmnConstants.SYNC_MODE_DATE);
    dailyBasicService.syncAll(CmnConstants.SYNC_MODE_DATE);
    splitAdjustTask.startCalc();


//    Thread tSar= new Thread(new Runnable() {
//      @Override
//      public void run() {
//        sarTask.startCalc();
//      }
//    },"thd-sar");
//    tSar.start();
//
//    Thread tSma = new Thread(new Runnable() {
//      @Override
//      public void run() {
//        smaTask.startCalc();
//      }
//    });
//    tSma.start();
//
//    try {
//      tSar.join();
//    } catch (InterruptedException e) {
//      log.error("",e);
//    }
//    try {
//      tSma.join();
//    } catch (InterruptedException e) {
//      log.error("",e);
//    }

    anaTagTask.startCalc(null);
    conceptService.sync();
    conceptDetailService.sync();

    return Result.wrapSuccessfulResult("OK");
  }
}
