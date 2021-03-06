package org.dcais.stock.stock.biz.ana.impl;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.tictactec.ta.lib.CoreAnnotated;
import com.tictactec.ta.lib.MAType;
import joinery.DataFrame;
import org.dcais.stock.stock.biz.IBaseServiceImpl;
import org.dcais.stock.stock.biz.ana.AnaCons;
import org.dcais.stock.stock.biz.ana.IDailyAnaTagService;
import org.dcais.stock.stock.biz.basic.IBasicService;
import org.dcais.stock.stock.biz.info.ISplitAdjustedDailyService;
import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.common.utils.*;
import org.dcais.stock.stock.dao.mybatisplus.ana.DailyAnaTagMapper;
import org.dcais.stock.stock.entity.ana.DailyAnaTag;
import org.dcais.stock.stock.entity.basic.Basic;
import org.dcais.stock.stock.entity.info.SplitAdjustedDaily;
import org.dcais.stock.stock.entity.tec.TecMa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 分析打标 服务实现类
 * </p>
 *
 * @author david-cai
 * @since 2020-06-06
 */
@Service
public class DailyAnaTagServiceImpl extends IBaseServiceImpl<DailyAnaTagMapper, DailyAnaTag> implements IDailyAnaTagService {
  @Autowired
  private IBasicService basicService;
  @Autowired
  private ISplitAdjustedDailyService splitAdjustedDailyService;



  String getMark(Boolean b){
    if(b == null){
      return StringUtil.EMPTY_STRING;
    }
    return b ? "Y":"N";
  }


  public String[] getSmaPeriods(){
    return new String[]{"5","8","16","17", "25", "50","100", "150","200","145","320","730"};
  }
  public String[] getVolPeriods(){
    return new String[]{"5","10", "24"};
  }

  @Override
  public Map ana(String tsCode, LocalDateTime lteDate ){
    Basic stock = basicService.getByTsCode(tsCode);

    LocalDateTime gteDate = LocalDateTime.now().minusYears(4);
    DataFrame df = this.getDataFrame(tsCode,gteDate,lteDate);
    List<TecMa> list = null;
    List<BigDecimal> ma = null;

    String[] smaTecPeriods= getSmaPeriods();

    LocalDateTime tradeDate = (LocalDateTime) df.get(df.length()-1,"tradeDate");

    this.remove(Wrappers.<DailyAnaTag>lambdaQuery()
      .eq(DailyAnaTag::getTsCode,tsCode)
      .eq(DailyAnaTag::getTradeDate,tradeDate)
    );

    Map<String,Object> anaResult = new HashMap<>();
    anaResult.put(AnaCons.ANA_CONS_TRADE_DATE,tradeDate);
    anaResult.put(AnaCons.ANA_CONS_TS_CODE,tsCode);
    anaResult.put(AnaCons.ANA_CONS_SYMBOL,stock.getSymbol());
    anaResult.put(AnaCons.ANA_CONS_NAME,stock.getName());
    anaResult.put(AnaCons.ANA_CONS_INDUSTRY,stock.getIndustry());
    anaResult.put(AnaCons.ANA_CONS_EXCHANGE,stock.getExchange());

    anaResult.put(AnaCons.ANA_CONS_OPEN, df.get(df.length()-1,"open"));
    anaResult.put(AnaCons.ANA_CONS_HIGH, df.get(df.length()-1,"high"));
    anaResult.put(AnaCons.ANA_CONS_LOW, df.get(df.length()-1,"low"));
    anaResult.put(AnaCons.ANA_CONS_CLOSE, df.get(df.length()-1,"close"));
    anaResult.put(AnaCons.ANA_CONS_SAR, df.get(df.length()-1,"sar"));
    anaResult.put(AnaCons.ANA_CONS_CCI, df.get(df.length()-1,"cci"));
    anaResult.put(AnaCons.ANA_CONS_MACD, df.get(df.length()-1,"macd"));
    anaResult.put(AnaCons.ANA_CONS_MACDSIGNAL, df.get(df.length()-1,"macdSignal"));
    anaResult.put(AnaCons.ANA_CONS_MACDHIST, df.get(df.length()-1,"macdHist"));

    Integer volumeToday = (Integer) df.get(df.length()-1, "vol");
    Integer volumeYesterday = (Integer) df.get(df.length()-2, "vol");

    {
      Double volRatio = (double)volumeToday/(double)volumeYesterday;
      anaResult.put(AnaCons.ANA_CONS_VOLUMN_RATIO,volRatio);
    }

    for(String period : getVolPeriods()){
      String columnName = "vol"+period;
      String key = AnaCons.ANA_CONS_VOLUMN_RATIO+ period;
      BigDecimal volumeMA = (BigDecimal) df.get(df.length()-1, columnName);
      Double volRatio = (double)volumeToday/volumeMA.doubleValue();
      anaResult.put(key,volRatio);
    }


    Boolean rSigToday = isBigDecimalOver(df,df.length()-1,"sar", df.length()-1, "close");
    Boolean rSigYestorday = isBigDecimalOver(df,df.length()-2,"sar", df.length()-2, "close");

    if(rSigToday && rSigYestorday){
      anaResult.put(AnaCons.ANA_CONS_SAR_SIGNAL, "S");
    }else if ( !rSigToday && !rSigYestorday) {
      anaResult.put(AnaCons.ANA_CONS_SAR_SIGNAL, "B");
    }else if ( !rSigYestorday && rSigToday ) {
      anaResult.put(AnaCons.ANA_CONS_SAR_SIGNAL, "BS");
    }else if( rSigYestorday && !rSigToday ) {
      anaResult.put(AnaCons.ANA_CONS_SAR_SIGNAL, "SB");
    }

    for(String period:smaTecPeriods){
      String key = AnaCons.ANA_CONS_SMA_TREND_PRE + period;
      String tecName = "Sma"+ period;
      anaResult.put(AnaCons.ANA_CONS_SMA_PRE+period, df.get(df.length()-1,tecName));
      Boolean res = isBigDecimalOver(df,df.length()-1,tecName,df.length()-2,tecName);
      anaResult.put(key,getMark(res));
    }

    String[][] crossPeriods = {{"8","17"},{"8","16"},{"8","25"},{"17","25"},{"25","50"},{"50","150"},{"150","200"}};

    for(String[] periods: crossPeriods){
      String shortP = periods[0];
      String longP = periods[1];
      String tecNameShortP = "Sma"+shortP;
      String tecNameLongP = "Sma"+longP;

      Boolean isShortOverLongPre =  isBigDecimalOver(df, df.length()-2 , tecNameShortP,df.length()-2, tecNameLongP);
      Boolean isShortOverLong =  isBigDecimalOver(df, df.length()-1 , tecNameShortP,df.length()-1, tecNameLongP );

      if( isShortOverLong == null){
      }else if (isShortOverLong){
        anaResult.put(AnaCons.ANA_CONS_SMA_SL_PRE+shortP+"_"+longP,"OVER");
      }else{
        anaResult.put(AnaCons.ANA_CONS_SMA_SL_PRE+shortP+"_"+longP,"UNDER");
      }

      anaResult.put(AnaCons.ANA_CONS_SMA_OVER_PRE+shortP+"_"+longP , getMark(isShortOverLong));

      String goldKey = AnaCons.ANA_CONS_SMA_GLOD_CROSS_PRE+shortP+"_"+longP;

      if(Boolean.FALSE.equals(isShortOverLongPre) && Boolean.TRUE.equals(isShortOverLong)){
        anaResult.put(goldKey,getMark(Boolean.TRUE));
      }else {
        anaResult.put(goldKey,getMark(Boolean.FALSE));
      }

      String deadKey = AnaCons.ANA_CONS_SMA_DEAD_CROSS_PRE+shortP+"_"+longP;
      if(Boolean.TRUE.equals( isShortOverLongPre) && Boolean.FALSE.equals( isShortOverLong)){
        anaResult.put(deadKey,getMark(Boolean.TRUE));
      }else {
        anaResult.put(deadKey,getMark(Boolean.FALSE));
      }
    }

    this.saveAnaResult(tsCode,tradeDate,anaResult);

    BigDecimal lastClose = (BigDecimal) df.get(df.length()-1,"close");
    BigDecimal last50Close = (BigDecimal) df.get(df.length() - 50 , "close");
    BigDecimal diff = MathUtil.subtract(lastClose,last50Close);
    BigDecimal rate50 = diff.divide(last50Close,6, RoundingMode.DOWN);
    Map<String,Object> item = new HashMap<>();

    item.put("tsCode",tsCode);
    item.put("rate50",rate50);
    item.put("tradeDate",tradeDate);
    return item;
  }

  public void saveAnaResult(String tsCode, LocalDateTime tradeDate , Map<String,Object> anaResults) {
    List<DailyAnaTag> dailyAnaTags = new LinkedList<>();
    for(Map.Entry<String,Object> entry : anaResults.entrySet()){
      DailyAnaTag dailyAnaTag = new DailyAnaTag();
      String key = entry.getKey();
      Object value = entry.getValue();

      dailyAnaTag.setTradeDate(tradeDate);
      dailyAnaTag.setTagKey(key);
      if( BigDecimal.class.isAssignableFrom(value.getClass()) ) {
        dailyAnaTag.setValue((BigDecimal) value);
      }else if ( String.class.isAssignableFrom(value.getClass()) ) {
        dailyAnaTag.setStrValue((String) value);
      }
      dailyAnaTags.add(dailyAnaTag);
    }
    this.saveBatch(dailyAnaTags);
  }

  public Boolean isBigDecimalOver(DataFrame df, int row1,String col1, int row2,String col2){
    BigDecimal v1 = (BigDecimal)df.get(row1,col1);
    if(v1 == null){
      return false;
    }
    BigDecimal v2 = (BigDecimal)df.get(row2,col2);
    if(v2 == null)
    {
      return false;
    }
    return  v1.compareTo(v2) > 0;
  }


  public DataFrame getDataFrame(String tsCode,LocalDateTime gteDate, LocalDateTime lteDate) {
    Result<List<SplitAdjustedDaily>> result  = splitAdjustedDailyService.getSplitAdjustDailyList(tsCode, LocalDateUtils.asDate(gteDate), LocalDateUtils.asDate(lteDate));
    if(!result.isSuccess()){
      log.error(result.getErrorMsg());
      return null;
    }
    DataFrame df = convToDF(result.getData());
    CoreAnnotated coreAnnotated = new CoreAnnotated();
    for (String period : getSmaPeriods()){

      String columnName = "Sma"+period;
      List<BigDecimal> closes =  df.col("close");
      List<BigDecimal> mas = TalibUtil.movingAverage(closes, MAType.Sma,Integer.valueOf(period));
      df.add(columnName,mas);
    }

    List<BigDecimal> closes =  df.col("close");
    List<BigDecimal> highs=  df.col("high");
    List<BigDecimal> lows=  df.col("low");

    for(String period : getVolPeriods()){
      String columnName = "vol"+period;
      List<Integer> vols =  df.col("vol");
      List<BigDecimal> bigVols = vols.stream().map(BigDecimal::new).collect(Collectors.toList());
      List<BigDecimal> mas= TalibUtil.movingAverage(bigVols,MAType.Sma,Integer.valueOf(period));
      df.add(columnName,mas);
    }

    List<BigDecimal> CCI = TalibUtil.CCI(highs,lows,closes,14);
    df.add("cci",CCI);


    Map<String,List<BigDecimal>> mapMACD = TalibUtil.macd(closes,12,26,9);
    List<BigDecimal> macd = mapMACD.get("macd");
    List<BigDecimal> macdSignal = mapMACD.get("signal");
    List<BigDecimal> macdHist = mapMACD.get("hist");

    df.add("macd",macd);
    df.add("macdSignal",macdSignal);
    df.add("macdHist",macdHist);

    List<BigDecimal> sars = TalibUtil.sar(highs,lows,0.1d,2d);
    df.add("sar",sars);
    return  df;
  }

  public DataFrame convToDF(List<SplitAdjustedDaily> datas){
    DataFrame<Object> df = new DataFrame<>("tsCode","tradeDate", "open","high","low","close","preClose","vol","amount","pctChg");
    datas.forEach(daily->{
      List<Object> obj = new ArrayList<>();
      obj.add(daily.getTsCode());
      obj.add(daily.getTradeDate());
      obj.add(daily.getOpen());
      obj.add(daily.getHigh());
      obj.add(daily.getLow());
      obj.add(daily.getClose());
      obj.add(daily.getPreClose());
      obj.add(daily.getVol());
      obj.add(daily.getAmount());
      obj.add(daily.getPctChg());
      df.append(obj);
    });
    return df;
  }

  public Map<String,Integer> getDataframeColumsMap(DataFrame df){
    Set<String> colums =  df.columns();
    Map<String,Integer> map = new HashMap<>();
    Integer i = 0;
    for( String columnName :colums){
      map.put(columnName,i);
      i++;
    }
    return map;
  }


  @Override
  public void anaCompare(LocalDateTime tradeDate, List<Map> items) {
    List<Map> itemsTradeDay = items.stream().filter(item -> {
      LocalDateTime itemTradeDate = (LocalDateTime) item.get("tradeDate");
      return (LocalDateUtils.formatToStr(itemTradeDate, DateUtils.YMD).equals(LocalDateUtils.formatToStr(tradeDate, DateUtils.YMD)));
    }).collect(Collectors.toList());
    this.anaRS50(tradeDate,itemsTradeDay);
  }
  @Override
  public void anaRS50(LocalDateTime tradeDate, List<Map> items){
    List<Map> sortedRates = items.stream().sorted((l,r)-> {
      BigDecimal left = (BigDecimal) l.get("rate50");
      BigDecimal right = (BigDecimal) r.get("rate50");
      return left.compareTo(right);
    }).collect(Collectors.toList());

    int size = sortedRates.size();
    List<DailyAnaTag> rs50tags = new LinkedList<>();
    for(int i = 0 ; i< sortedRates.size() ; i ++ ){
      Map<String,Object> item = sortedRates.get(i);
      BigDecimal rs = MathUtil.divide(new BigDecimal(i).multiply(new BigDecimal(100)),new BigDecimal(size));
      item.put("RS50",rs);
      DailyAnaTag dailyAnaTag = new DailyAnaTag();
      dailyAnaTag.setTsCode((String) item.get("tsCode"));
      dailyAnaTag.setTradeDate(tradeDate);
      dailyAnaTag.setTagKey("RS50");
      dailyAnaTag.setValue(rs);
      rs50tags.add(dailyAnaTag);
    }
    this.saveBatch(rs50tags);

  }
}
