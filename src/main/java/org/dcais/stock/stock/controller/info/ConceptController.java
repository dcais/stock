package org.dcais.stock.stock.controller.info;

import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.info.ConceptDetailService;
import org.dcais.stock.stock.biz.info.ConceptService;
import org.dcais.stock.stock.common.cons.CmnConstants;
import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.common.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RequestMapping("concept")
@Slf4j
@RestController
public class ConceptController {
  @Autowired
  private ConceptService conceptService;
  @Autowired
  private ConceptDetailService conceptDetailService;

  @RequestMapping(value = "/sync", method = RequestMethod.GET)
  @ResponseBody
  public Result syncAll(String mode) {
    conceptService.sync();
    conceptDetailService.sync();
    return Result.wrapSuccessfulResult("OK");
  }

}
