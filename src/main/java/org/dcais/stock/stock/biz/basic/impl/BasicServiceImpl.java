package org.dcais.stock.stock.biz.basic.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.BaseServiceImpl;
import org.dcais.stock.stock.biz.BizConstans;
import org.dcais.stock.stock.biz.basic.BasicService;
import org.dcais.stock.stock.biz.tushare.StockInfoService;
import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.common.utils.JsonUtil;
import org.dcais.stock.stock.common.utils.ListUtil;
import org.dcais.stock.stock.dao.basic.BasicDao;
import org.dcais.stock.stock.entity.basic.Basic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.plaf.ListUI;


@Slf4j
@Service
public class BasicServiceImpl extends BaseServiceImpl implements BasicService {

  @Autowired
  private BasicDao basicDao;
  @Autowired
  private StockInfoService stockInfoService;

  public List<Basic> getAll() {
    return super.getAll(basicDao);
  }

  public List<Basic> select(Map<String,Object> param){
    return basicDao.select(param);
  }

  public Integer selectCount(Map<String,Object> param){
    return basicDao.selectCount(param);
  }

  public Basic getById(Long id) {
    return super.getById(basicDao, id);
  }

  public boolean save(Basic basic) {
    return super.save(basicDao, basic);
  }


  public boolean deleteById(Long id) {
    return super.deleteById(basicDao, id);
  }

  public int deleteByIds(Long[] ids) {
    return super.deleteByIds(basicDao, ids);
  }


  @Override
  public Result sync() {
    Function<String,Void> syncStatus = status -> {
      Result rStockInfo =  stockInfoService.stockBasicInfo(status);
      if(!rStockInfo.isSuccess()){
        log.error(JsonUtil.toJson(rStockInfo));
        return null;
      }
      List<Basic> basics = (List<Basic>) rStockInfo.getData();
      Function<Basic, Void> saveToDb = basic -> {
        Map<String, Object> param = new HashMap<>();
        param.put("isDeleted", "N");
        param.put("tsCode", basic.getTsCode());
        List<Basic> tmps = this.select(param);
        if (tmps.size() > 0) {
          Basic tmp = tmps.get(0);
          basic.setId(tmp.getId());
        }
        this.save(basic);
        return null;
      };
      basics.forEach(saveToDb::apply);
      return null;
    };

    String[] status = {BizConstans.LIST_STATUS_L,BizConstans.LIST_STATUS_D,BizConstans.LIST_STATUS_P};
    Arrays.stream(status).forEach(syncStatus::apply);
    return Result.wrapSuccessfulResult("OK");
  }

  @Override
  public Basic getBySymbol(String symbol) {
    Map<String, Object> param = new HashMap<>();
    param.put("isDeleted", "N");
    param.put("symbol", symbol);
    List<Basic> tmps = this.select(param);
    if(ListUtil.isBlank(tmps)){
      return null;
    }
    return tmps.get(0);
  }
}
