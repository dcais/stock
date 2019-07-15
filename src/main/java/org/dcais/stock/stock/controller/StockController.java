package org.dcais.stock.stock.controller;

import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.basic.BasicService;
import org.dcais.stock.stock.biz.info.DailyService;
import org.dcais.stock.stock.biz.basic.TradeCalService;
import org.dcais.stock.stock.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value="/")
public class StockController {
    @Autowired
    private BasicService basicService;
    @Autowired
    private TradeCalService tradeCalService;
    @Autowired
    private DailyService dailyService;

    @RequestMapping(value="/syncBasic",method = RequestMethod.GET)
    @ResponseBody
    public Result syncBasic(){
        return basicService.sync();
    }

    @RequestMapping(value="/syncTradeCal",method = RequestMethod.GET)
    @ResponseBody
    public Result syncTradeCal(){
        return tradeCalService.sync();
    }

    @RequestMapping(value="/syncDailyBySymbol",method = RequestMethod.GET)
    @ResponseBody
    public Result syncDailyBySymbol(@RequestParam(required = true) String symbol){
        return dailyService.syncHistory(symbol);
    }

    @RequestMapping(value="/logLevelTest",method = RequestMethod.GET)
    @ResponseBody
    public Result logLevelTest() {
        log.info("info");
        log.debug("debug");
//        return tradeCalService.sync();
        return Result.wrapSuccessfulResult("");
    }

}
