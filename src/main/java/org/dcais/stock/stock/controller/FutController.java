package org.dcais.stock.stock.controller;

import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.future.FutExchangeService;
import org.dcais.stock.stock.biz.future.FutService;
import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.entity.basic.FutExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/fut")
public class FutController {
  @Autowired
  public FutExchangeService futExchangeService;
  @Autowired
  public FutService futService;

  @RequestMapping(value = "/exchangeList", method = RequestMethod.GET)
  @ResponseBody
  public Result exchanges(){
    List<FutExchange> futExchangeList = futExchangeService.getAll();
    return Result.wrapSuccessfulResult(futExchangeList);
  }
  @RequestMapping(value = "/syncBasic", method = RequestMethod.GET)
  public Result syncBasic(){
    futService.syncBasic();
    return Result.wrapSuccessfulResult("OK");
  }

  @RequestMapping(value = "/sync", method = RequestMethod.GET)
  @ResponseBody
  public Result sync(){
    return Result.wrapSuccessfulResult("OK");
  }
}
