package org.dcais.stock.stock.biz.info.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.IBaseServiceImpl;
import org.dcais.stock.stock.biz.info.IAdjFactorService;
import org.dcais.stock.stock.biz.info.IDailyService;
import org.dcais.stock.stock.biz.info.ISplitAdjustedDailyService;
import org.dcais.stock.stock.common.cons.StockMetaConstant;
import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.common.utils.DateUtils;
import org.dcais.stock.stock.common.utils.LocalDateUtils;
import org.dcais.stock.stock.common.utils.MathUtil;
import org.dcais.stock.stock.common.utils.StringUtil;
import org.dcais.stock.stock.dao.mybatisplus.info.SplitAdjustedDailyMapper;
import org.dcais.stock.stock.dao.xdriver.meta.XStockMetaDao;
import org.dcais.stock.stock.entity.info.AdjFactor;
import org.dcais.stock.stock.entity.info.Daily;
import org.dcais.stock.stock.entity.info.SplitAdjustedDaily;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SplitAdjustServiceImpl extends IBaseServiceImpl<SplitAdjustedDailyMapper, SplitAdjustedDaily> implements ISplitAdjustedDailyService {
  @Autowired
  private IAdjFactorService adjFactorService;
  @Autowired
  private XStockMetaDao xStockMetaDao;
  @Autowired
  private SplitAdjustedDailyMapper splitAdjustedDailyMapper;
  @Value("${stock.batch-size:1000}")
  private Integer batchSize;
  @Autowired
  IDailyService dailyService;


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
      splitAdjustedDailyMapper.delete(Wrappers.<SplitAdjustedDaily>lambdaQuery().eq(SplitAdjustedDaily::getTsCode,tsCode));
    }

    SplitAdjustedDaily lastDaily= this.getOne(
      Wrappers.<SplitAdjustedDaily>lambdaQuery()
        .eq(SplitAdjustedDaily::getTsCode,tsCode)
        .orderByDesc(SplitAdjustedDaily::getTradeDate)
        .last("limit 1")
    );

    LocalDateTime lastTradeDate =  null;
    if(lastDaily != null ){
      lastTradeDate = lastDaily.getTradeDate();
    }

    while(true){
      LambdaQueryWrapper<Daily> wrapper =
        Wrappers.<Daily>lambdaQuery()
          .eq(Daily::getTsCode, tsCode);

      wrapper = wrapper.gt(lastTradeDate!= null,Daily::getTradeDate, lastTradeDate);
      wrapper = wrapper.orderByAsc(Daily::getTradeDate);
      wrapper.last("limit "+ batchSize);

      List<Daily> dailyList = dailyService.list(wrapper);
      if(dailyList.size() == 0 ){
        break;
      }
      Daily daily = dailyList.get(dailyList.size()-1);
      lastTradeDate = daily.getTradeDate();

        List<AdjFactor> adjFactors = adjFactorService.list(
        Wrappers.<AdjFactor>lambdaQuery()
          .eq(AdjFactor::getTsCode,tsCode)
          .le(false,AdjFactor::getTradeDate,daily.getTradeDate())
          .ge(AdjFactor::getTradeDate,dailyList.get(0).getTradeDate())
      );

      Map<String,AdjFactor> mapAdjfactor
        =  adjFactors.stream().collect(Collectors.toMap(
          x-> DateUtils.dateFormat(LocalDateUtils.asDate(x.getTradeDate()),DateUtils.YMD)
        , Function.identity()
        , (oldV,newV) -> newV
      ));

      List<SplitAdjustedDaily> splits =
        dailyList.stream().map( t-> {
          String key = LocalDateUtils.formatToStr(t.getTradeDate(), DateUtils.YMD);
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


      this.saveBatch(splits);
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
    LambdaQueryWrapper<SplitAdjustedDaily> wrapper = Wrappers.<SplitAdjustedDaily>lambdaQuery().eq(SplitAdjustedDaily::getTsCode,tsCode)
      .ge(SplitAdjustedDaily::getTradeDate,gteDate);
    if(lteDate != null ){
      wrapper = wrapper.le(SplitAdjustedDaily::getTradeDate,lteDate);
    }

    List<SplitAdjustedDaily> datas = this.list(wrapper);
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
