package org.dcais.stock.stock.biz.info.impl;

import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.info.AdjFactorService;
import org.dcais.stock.stock.biz.info.DailyService;
import org.dcais.stock.stock.biz.info.SplitAdjustService;
import org.dcais.stock.stock.common.cons.StockMetaConstant;
import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.common.utils.DateUtils;
import org.dcais.stock.stock.common.utils.MathUtil;
import org.dcais.stock.stock.common.utils.StringUtil;
import org.dcais.stock.stock.dao.xdriver.daily.XSplitAdjustedDailyDao;
import org.dcais.stock.stock.dao.xdriver.meta.XStockMetaDao;
import org.dcais.stock.stock.entity.info.AdjFactor;
import org.dcais.stock.stock.entity.info.Daily;
import org.dcais.stock.stock.entity.info.SplitAdjustedDaily;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SplitAdjustServiceImpl implements SplitAdjustService {
  @Autowired
  private AdjFactorService adjFactorService;
  @Autowired
  private XStockMetaDao xStockMetaDao;
  @Autowired
  private XSplitAdjustedDailyDao xSplitAdjustedDailyDao;
  @Value("${stock.batch-size:1000}")
  private Integer batchSize;
  @Autowired
  DailyService dailyService;


  @Override
  public Result calcSplitAdjust(String tsCode){
    return this.calcSplitAdjust(tsCode,false);
  }
  @Override
  public Result calcSplitAdjust(String tsCode,boolean forceRemove ){
    Result<AdjFactor> rAdjFactorLatest = adjFactorService.getMaxDaily(tsCode);
    if(!rAdjFactorLatest.isSuccess()){
      return Result.wrapErrorResult("","can not get latest adjust factor");
    }
    AdjFactor adjFactorLatest = rAdjFactorLatest.getData();
    if( isRecaculate(adjFactorLatest) || forceRemove){
      xSplitAdjustedDailyDao.remove(tsCode);
    }

    SplitAdjustedDaily splitAdjustedDaily = xSplitAdjustedDailyDao.getLatest(tsCode);

    Map<String,Object> params = new HashMap<>();
    params.put("tsCode",tsCode);
    params.put("isDeleted","N");
    if(splitAdjustedDaily != null){
      Date tradeDate = splitAdjustedDaily.getTradeDate();
      params.put("gtTradeDate", tradeDate);
    }
    params.put("sort","tradeDate");
    params.put("start",0);
    params.put("pageSize",batchSize);

    Map<String,Object> adjParams = new HashMap<>();
    adjParams.put("tsCode",params.get("tsCode"));
    adjParams.put("isDeleted","N");
    adjParams.put("gtTradeDate", params.get("gtTradeDate"));
    adjParams.put("sort","tradeDate");

    while(true){
      List<Daily> dailyList = dailyService.select(params);
      if(dailyList.size() == 0 ){
        break;
      }
      Daily daily = dailyList.get(dailyList.size()-1);
      adjParams.put("lteTradeDate", daily.getTradeDate());
      params.put("gtTradeDate", daily.getTradeDate());

      List<AdjFactor> adjFactors = adjFactorService.select(adjParams);
      Map<String,AdjFactor> mapAdjfactor
        =  adjFactors.stream().collect(Collectors.toMap(
          x-> DateUtils.dateFormat(x.getTradeDate(),DateUtils.YMD)
        , Function.identity()
        , (oldV,newV) -> newV
      ));

      List<SplitAdjustedDaily> splits =
        dailyList.stream().map( t-> {
          String key = DateUtils.dateFormat(t.getTradeDate(), DateUtils.YMD);
          AdjFactor adjFactorDaily = mapAdjfactor.get(key);
          if(adjFactorDaily == null ){
            String errMsg = "can not find daily adj factor. [tsCode]" + t.getTsCode() +"[tradeDate]"+ key;
            log.error(errMsg);
            throw new RuntimeException(errMsg);
          }
          try {
            return calc(t,adjFactorDaily,adjFactorLatest);
          } catch (Exception e){
            String errMsg = "split calc failed adj factor. [tsCode]" + t.getTsCode() +"[tradeDate]"+ key;
            log.error(errMsg,e);
            throw e;
          }
        }).collect(Collectors.toList());


      xSplitAdjustedDailyDao.insertList(splits);
    }
    saveLatestAdjFactorToStockMeta(adjFactorLatest);
    return Result.wrapSuccessfulResult("OK");
  }

  @Override
  public Result<List<SplitAdjustedDaily>> getSplitAdjustDailyList(String tsCode, Date gteDate,Date lteDate) {
    if(StringUtil.isBlank(tsCode)){
      return Result.wrapErrorResult("","no ts code");
    }
    if(gteDate == null ){
      gteDate = DateUtils.smartFormat("1970-01-01");
    }
    List<SplitAdjustedDaily> datas = this.xSplitAdjustedDailyDao.getFromDate(tsCode,gteDate,lteDate);
    return Result.wrapSuccessfulResult(datas);
  }

  private BigDecimal calc(BigDecimal d, BigDecimal adj, BigDecimal adjLatest){
    if(d== null || adj == null || adjLatest == null){
      return BigDecimal.ZERO;
    }
    if(adj.equals(adjLatest)){
      return d.multiply(BigDecimal.ONE);
    }
    return d.multiply(adj).divide(adjLatest,6,BigDecimal.ROUND_HALF_UP);
  }

  public SplitAdjustedDaily calc(Daily daily, AdjFactor dailyAdj, AdjFactor lastestAdj) {
    SplitAdjustedDaily adjDaily = new SplitAdjustedDaily();
    adjDaily.setDefaultBizValue();
    adjDaily.setOpen(calc(daily.getOpen(),dailyAdj.getAdjFactor(),lastestAdj.getAdjFactor()));
    adjDaily.setClose(calc(daily.getClose(),dailyAdj.getAdjFactor(),lastestAdj.getAdjFactor()));
    adjDaily.setHigh(calc(daily.getHigh(),dailyAdj.getAdjFactor(),lastestAdj.getAdjFactor()));
    adjDaily.setLow(calc(daily.getLow(),dailyAdj.getAdjFactor(),lastestAdj.getAdjFactor()));
    adjDaily.setPreClose(calc(daily.getPreClose(),dailyAdj.getAdjFactor(),lastestAdj.getAdjFactor()));
    adjDaily.setAmount(calc(daily.getAmount(),dailyAdj.getAdjFactor(),lastestAdj.getAdjFactor()));
    adjDaily.setVol(daily.getVol());
    adjDaily.setPctChg(daily.getPctChg());
    adjDaily.setTradeDate(daily.getTradeDate());
    adjDaily.setTsCode(daily.getTsCode());
    return  adjDaily;
  }

  public void saveLatestAdjFactorToStockMeta(AdjFactor adjFactor){
    String adjFactorStr =  MathUtil.toRateFormat(adjFactor.getAdjFactor());
    xStockMetaDao.setStockMeta(adjFactor.getTsCode(), StockMetaConstant.LAST_ADJFACTOR_STR, adjFactorStr);
  }

  public boolean isRecaculate(AdjFactor adjFactor){
    String tsCode = adjFactor.getTsCode();
    String lastAdjFactor = xStockMetaDao.getStockMeta(tsCode, StockMetaConstant.LAST_ADJFACTOR_STR);
    if(StringUtil.isBlank(lastAdjFactor)){
      return true;
    }
    if(!MathUtil.toRateFormat(adjFactor.getAdjFactor()).equals(lastAdjFactor)){
      return true;
    }
    return false;
  }

}
