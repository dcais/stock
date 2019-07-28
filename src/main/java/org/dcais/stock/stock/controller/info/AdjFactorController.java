package org.dcais.stock.stock.controller.info;

import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.info.AdjFactorService;
import org.dcais.stock.stock.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("adjFactor")
@Slf4j
@RestController
public class AdjFactorController {
    @Autowired
    private AdjFactorService adjFactorService;
    @RequestMapping(value="/syncBySymbol",method = RequestMethod.GET)
    @ResponseBody
    public Result syncBySymbol(@RequestParam(required = true) String symbol){
        return adjFactorService.syncHistory(symbol);
    }

    @RequestMapping(value="/syncAll",method = RequestMethod.GET)
    @ResponseBody
    public Result syncAll(){
        adjFactorService.syncAll();
        return Result.wrapSuccessfulResult("OK");
    }
}
