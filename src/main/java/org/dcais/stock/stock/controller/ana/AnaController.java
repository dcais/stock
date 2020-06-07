package org.dcais.stock.stock.controller.ana;

import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.basic.ITradeCalService;
import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.common.utils.LocalDateUtils;
import org.dcais.stock.stock.common.utils.StringUtil;
import org.dcais.stock.stock.task.AnaTagTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping(value = "/ana")
public class AnaController {
  @Autowired
  private AnaTagTask anaTagTask;
  @Autowired
  private ITradeCalService tradeCalService;

  @RequestMapping(value = "/anaTag", method = RequestMethod.GET)
  @ResponseBody
  public Result start(String tradeDate) {
    LocalDateTime dTradeDate= null;
    if(StringUtil.isNotBlank(tradeDate)){
      dTradeDate = LocalDateUtils.smartFormat(tradeDate);
    }
    anaTagTask.startCalc(dTradeDate);
    return Result.wrapSuccessfulResult("OK");
  }

}
