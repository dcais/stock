package org.dcais.stock.stock.controller.info;

import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.info.IConceptDetailService;
import org.dcais.stock.stock.biz.info.IConceptService;
import org.dcais.stock.stock.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("concept")
@Slf4j
@RestController
public class ConceptController {
  @Autowired
  private IConceptService conceptService;
  @Autowired
  private IConceptDetailService conceptDetailService;

  @RequestMapping(value = "/sync", method = RequestMethod.GET)
  @ResponseBody
  public Result syncAll(String mode) {
    conceptService.sync();
    conceptDetailService.sync();
    return Result.wrapSuccessfulResult("OK");
  }

}
