package org.dcais.stock.stock.controller.ana;

import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.ana.AnaTagService;
import org.dcais.stock.stock.biz.basic.TradeCalService;
import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.task.AnaTagTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Slf4j
@RestController
@RequestMapping(value = "/ana")
public class AnaController {
  @Autowired
  private AnaTagTask anaTagTask;
  @Autowired
  private AnaTagService anaTagService;
  @Autowired
  private TradeCalService tradeCalService;

  @RequestMapping(value = "/anaTag", method = RequestMethod.GET)
  @ResponseBody
  public Result start() {
    anaTagTask.startCalc();
    return Result.wrapSuccessfulResult("OK");
  }

}
