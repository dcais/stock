package org.dcais.stock.stock.biz.basic.impl;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.dcais.stock.stock.biz.BizConstans;
import org.dcais.stock.stock.biz.IBaseServiceImpl;
import org.dcais.stock.stock.biz.basic.ITradeCalService;
import org.dcais.stock.stock.biz.future.FutExchangeService;
import org.dcais.stock.stock.biz.tushare.StockInfoService;
import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.common.utils.LocalDateUtils;
import org.dcais.stock.stock.dao.mybatis.basic.TradeCalDao;
import org.dcais.stock.stock.dao.mybatisplus.basic.TradeCalMapper;
import org.dcais.stock.stock.entity.basic.FutExchange;
import org.dcais.stock.stock.entity.basic.TradeCal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.time.LocalDateTime;
import java.util.*;


@Service
public class TradeCalServiceImpl extends IBaseServiceImpl<TradeCalMapper, TradeCal> implements ITradeCalService {
  @Autowired
  private StockInfoService stockInfoService;
  @Autowired
  private FutExchangeService futExchangeService;

  @Value("${stock.batch-insert-size:1000}")
  private Integer batchInsertSize;

  public LocalDateTime selectMaxCalDate(String exchange){
    TradeCal tradeCal = this.getOne(Wrappers.<TradeCal>lambdaQuery().eq(TradeCal::getExchange, exchange).orderByDesc(TradeCal::getCalDate).last("limit 1"));
    if(tradeCal == null ){
      return null;
    }
    return tradeCal.getCalDate();
  }

  public void syncTradeCal(String exchange){
    LocalDateTime startDate = this.selectMaxCalDate(exchange);
    Result r = stockInfoService.tradeCal(exchange, LocalDateUtils.asDate(startDate), null);
    if (!r.isSuccess()) {
      return ;
    }

    List<TradeCal> tradeCals = (List<TradeCal>) r.getData();

    List<TradeCal> toSaveList = new LinkedList<>();
    tradeCals.forEach(tradeCal -> {
      List<TradeCal> tmps = this.list(Wrappers.<TradeCal>lambdaQuery()
        .eq(TradeCal::getExchange, tradeCal.getExchange())
        .eq(TradeCal::getCalDate, tradeCal.getCalDate())
      );
      if (tmps.size() > 0) {
        return;
      }
      toSaveList.add(tradeCal);
    });
    this.saveBatch(toSaveList,batchInsertSize);
  }

  @Override
  public Result sync() {
    String[] exchanges = {
      BizConstans.EXCHANGE_SSE,
      BizConstans.EXCHANGE_SZSE
    };

    Arrays.stream(exchanges).forEach(this::syncTradeCal);

    List<FutExchange> futExchanges = futExchangeService.getAll();
    futExchanges.forEach(futExchange -> {this.syncTradeCal(futExchange.getCode());});

    return Result.wrapSuccessfulResult("OK");
  }


  @Override
  public LocalDateTime getLastTradeDate(){
    String exchange = BizConstans.EXCHANGE_SSE;
    TradeCal tradeCal = this.getOne(Wrappers.<TradeCal>lambdaQuery()
      .eq(TradeCal::getExchange, exchange)
      .eq(TradeCal::getIsOpen, 1)
      .lt(TradeCal::getCalDate, LocalDateTime.now().minusHours(17))
      .orderByDesc(TradeCal::getCalDate)
      .last("limit 1")
    );

    if(tradeCal == null ){
      return null;
    }
    return tradeCal.getCalDate();
  }
}
