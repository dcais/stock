package org.dcais.stock.stock.biz.basic.impl;


import java.util.*;
import java.util.function.Function;

import org.dcais.stock.stock.biz.BaseServiceImpl;
import org.dcais.stock.stock.biz.BizConstans;
import org.dcais.stock.stock.biz.basic.TradeCalService;
import org.dcais.stock.stock.biz.tushare.StockInfoService;
import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.dao.mybatis.basic.TradeCalDao;
import org.dcais.stock.stock.entity.basic.TradeCal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TradeCalServiceImpl extends BaseServiceImpl implements TradeCalService {

  @Autowired
  private TradeCalDao calDateDao;
  @Autowired
  private StockInfoService stockInfoService;

  public List<TradeCal> getAll() {
    return super.getAll(calDateDao);
  }

  public List<TradeCal> select(Map<String, Object> param) {
    return calDateDao.select(param);
  }

  public Integer selectCount(Map<String, Object> param) {
    return calDateDao.selectCount(param);
  }

  public TradeCal getById(Long id) {
    return super.getById(calDateDao, id);
  }

  public boolean save(TradeCal calDate) {
    return super.save(calDateDao, calDate);
  }

  public boolean deleteById(Long id) {
    return super.deleteById(calDateDao, id);
  }

  public int deleteByIds(Long[] ids) {
    return super.deleteByIds(calDateDao, ids);
  }

  @Override
  public Result sync() {
    String[] exchanges = {BizConstans.EXCHANGE_SSE, BizConstans.EXCHANGE_SZSE};

    Function<String, Void> f = exchange -> {
      Date startDate = calDateDao.selectMaxCalDate(exchange);
      Result r = stockInfoService.tradeCal(exchange, startDate, null);
      if (!r.isSuccess()) {
        return null;
      }

      List<TradeCal> tradeCals = (List<TradeCal>) r.getData();
      tradeCals.forEach(tradeCal -> {
        Map<String, Object> params = new HashMap<>();
        params.put("isDeleted", "N");
        params.put("exchange", tradeCal.getExchange());
        params.put("calDate", tradeCal.getCalDate());

        List<TradeCal> tmps = calDateDao.select(params);
        if (tmps.size() > 0) {
          return;
        }
        this.save(tradeCal);
      });
      return null;
    };
    Arrays.stream(exchanges).forEach(f::apply);
    return Result.wrapSuccessfulResult("OK");
  }
}
