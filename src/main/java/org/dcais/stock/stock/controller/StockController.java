package org.dcais.stock.stock.controller;

import org.dcais.stock.stock.biz.tushare.StockInfoService;
import org.dcais.stock.stock.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/hello")
public class StockController {
    @Autowired
    private StockInfoService stockInfoService;

    @RequestMapping(value="/world",method = RequestMethod.GET)
    @ResponseBody
    public Result hello(){
        Object a = stockInfoService.stockBasicInfo();
        return  Result.wrapSuccessfulResult(a);
    }
}
