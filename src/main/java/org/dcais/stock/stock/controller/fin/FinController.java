package org.dcais.stock.stock.controller.fin;

import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.basic.BasicService;
import org.dcais.stock.stock.biz.info.FinService;
import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.entity.basic.Basic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("fin")
@Slf4j
@RestController
public class FinController {
  @Autowired
  private BasicService basicService;
  @Autowired
  private FinService finService;

  @RequestMapping(value = "/income/sync", method = RequestMethod.GET)
  @ResponseBody
  public Result incomeSync(){
    List<Basic> list = basicService.getAllList();

    list.forEach( basic -> {
      finService.syncFinIncome(basic.getTsCode());
    });

    return Result.wrapSuccessfulResult("OK");
  }
}

