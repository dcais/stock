package org.dcais.stock.stock.controller.info;

import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.info.DailyService;
import org.dcais.stock.stock.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("daily")
@Slf4j
@RestController
public class DailyController {
    @Autowired
    private DailyService dailyService;
    @RequestMapping(value="/syncBySymbol",method = RequestMethod.GET)
    @ResponseBody
    public Result syncBySymbol(@RequestParam(required = true) String symbol){
        return dailyService.syncHistory(symbol);
    }

    @RequestMapping(value="/syncAll",method = RequestMethod.GET)
    @ResponseBody
    public Result syncAll() {
        dailyService.syncAll();
        return Result.wrapSuccessfulResult("OK");
    }
}
