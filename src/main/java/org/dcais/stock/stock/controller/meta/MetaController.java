package org.dcais.stock.stock.controller.meta;

import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.dao.xdriver.meta.XMetaCollDao;
import org.dcais.stock.stock.params.MetaSetParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("meta")
@RestController
public class MetaController {
  @Autowired
  private XMetaCollDao xMetaCollDao;

  @RequestMapping("set")
  @ResponseBody
  public Result set(@RequestBody @Valid  MetaSetParam param){
    xMetaCollDao.put(param.getKey(),param.getValue());
    return Result.wrapSuccessfulResult("");
  }

  @RequestMapping("get")
  @ResponseBody
  public Result get(String key) {
    Object value = xMetaCollDao.get(key);
    return Result.wrapSuccessfulResult(value);
  }
}
