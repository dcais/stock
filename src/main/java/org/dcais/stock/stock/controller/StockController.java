package org.dcais.stock.stock.controller;

import org.dcais.stock.stock.biz.basic.BasicService;
import org.dcais.stock.stock.biz.tushare.StockInfoService;
import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.entity.basic.Basic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/")
public class StockController {
    @Autowired
    private BasicService basicService;

    @RequestMapping(value="/syncBasic",method = RequestMethod.GET)
    @ResponseBody
    public Result syncBasic(){
        return basicService.sync();
    }

}
