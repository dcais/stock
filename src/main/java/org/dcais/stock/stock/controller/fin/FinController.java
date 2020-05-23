package org.dcais.stock.stock.controller.fin;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.BizConstans;
import org.dcais.stock.stock.biz.basic.IBasicService;
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
  private IBasicService basicService;
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

  @RequestMapping(value = "/indicator/sync", method = RequestMethod.GET)
  @ResponseBody
  public Result indicatorSync(){
    List<Basic> list = basicService.getAllList();

    list.forEach( basic -> {
      finService.syncFinIndicator(basic.getTsCode());
    });

    return Result.wrapSuccessfulResult("OK");
  }

  @RequestMapping(value = "/indicatorRate/calc", method = RequestMethod.GET)
  @ResponseBody
  public Result indicatorRateCalc(){
    finService.calcFinIndicatorRate(2020,1);
    for( int i = 2019; i > 1999 ; i-- ){
      for( int j = 4 ; j > 0 ; j-- ){
        finService.calcFinIndicatorRate(i,j);
      }
    }
    return  Result.wrapSuccessfulResult("OK");
  }

  @RequestMapping(value="getCANSLIMCandidates")
  @ResponseBody
  public Result getCANSLIMCandidates(Integer year ,Integer season){
    return finService.getCANSLIMCandidates(year,season);
  }



}

