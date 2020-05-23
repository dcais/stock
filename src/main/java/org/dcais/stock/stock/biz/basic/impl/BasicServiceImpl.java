package org.dcais.stock.stock.biz.basic.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.BizConstans;
import org.dcais.stock.stock.biz.basic.IBasicService;
import org.dcais.stock.stock.biz.tushare.StockInfoService;
import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.common.utils.JsonUtil;
import org.dcais.stock.stock.dao.mybatisplus.basic.BasicMapper;
import org.dcais.stock.stock.entity.basic.Basic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;


@Slf4j
@Service
public class BasicServiceImpl extends ServiceImpl<BasicMapper, Basic> implements IBasicService {

  @Autowired
  private StockInfoService stockInfoService;

  @Override
  public Result sync() {
    Function<String, Void> syncStatus = status -> {
      Result rStockInfo = stockInfoService.stockBasicInfo(status);
      if (!rStockInfo.isSuccess()) {
        log.error(JsonUtil.toJson(rStockInfo));
        return null;
      }
      List<Basic> basics = (List<Basic>) rStockInfo.getData();

      for(Basic basic: basics){
        Basic tmp = this.getByTsCode(basic.getTsCode());
        if(tmp == null){
          continue;
        }
        basic.setId(tmp.getId());
      }
      this.saveOrUpdateBatch(basics);

      return null;
    };

    String[] status = {BizConstans.LIST_STATUS_L, BizConstans.LIST_STATUS_D, BizConstans.LIST_STATUS_P};
    Arrays.stream(status).forEach(syncStatus::apply);
    return Result.wrapSuccessfulResult("OK");
  }

  @Override
  public List<Basic> getAllList() {
    return this.list(Wrappers.<Basic>lambdaQuery().eq(Basic::getListStatus,BizConstans.LIST_STATUS_L));
  }

  @Override
  public Basic getBySymbol(String symbol) {
    return this.getOne(Wrappers.<Basic>lambdaQuery().eq(Basic::getSymbol,symbol));
  }

  @Override
  public Basic getByTsCode(String tsCode){
    return this.getOne(Wrappers.<Basic>lambdaQuery().eq(Basic::getTsCode,tsCode));
  }
}
