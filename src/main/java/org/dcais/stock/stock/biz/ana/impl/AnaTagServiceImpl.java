package org.dcais.stock.stock.biz.ana.impl;

import joinery.DataFrame;
import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.ana.AnaCons;
import org.dcais.stock.stock.biz.ana.AnaTagService;
import org.dcais.stock.stock.biz.basic.BasicService;
import org.dcais.stock.stock.biz.info.SplitAdjustService;
import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.common.utils.DateUtils;
import org.dcais.stock.stock.common.utils.StringUtil;
import org.dcais.stock.stock.dao.xdriver.ana.XAnaTagDao;
import org.dcais.stock.stock.dao.xdriver.tec.XSMADao;
import org.dcais.stock.stock.dao.xdriver.tec.XTecSARDao;
import org.dcais.stock.stock.entity.basic.Basic;
import org.dcais.stock.stock.entity.info.SplitAdjustedDaily;
import org.dcais.stock.stock.entity.tec.BaseTec;
import org.dcais.stock.stock.entity.tec.TecMa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AnaTagServiceImpl implements AnaTagService {
  @Autowired
  private BasicService basicService;
  @Autowired
  private SplitAdjustService splitAdjustService;
  @Autowired
  private XSMADao xsmaDao;
  @Autowired
  private XTecSARDao xTecSARDao;
  @Autowired
  private XAnaTagDao xAnaTagDao;

  String getMark(Boolean b){
    if(b == null){
      return StringUtil.EMPTY_STRING;
    }
    return b ? "Y":"N";
  }

  @Override
  public void ana(String tsCode){
    Basic stock = basicService.getByTsCode(tsCode);
    Calendar ca = Calendar.getInstance();
    ca.setTime(DateUtils.getStartTimeDate(new Date()));
    ca.add(Calendar.MONTH,-3);
    Date gteDate = ca.getTime();
    DataFrame df = this.getDataFrame(tsCode,gteDate);
    List<TecMa> list = null;
    List<BigDecimal> ma = null;

    String[] smaTecPeriods= {"8","17","25","99"};

    for(String period:smaTecPeriods){
      String tecName = "sma"+period;
      list = xsmaDao.getFromDate(tsCode, tecName,gteDate);
      ma = list.stream().map(BaseTec::getValue).collect(Collectors.toList());
      df.add(tecName,ma);
    }

    list = xTecSARDao.getFromDate(tsCode,"sar", gteDate);
    ma = list.stream().map(BaseTec::getValue).collect(Collectors.toList());
    df.add("sar",ma);


    Date tradeDate = (Date) df.get(df.length()-1,"tradeDate");

    xAnaTagDao.remove(tsCode,tradeDate);

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
      String tecName = "sma"+ period;
      anaResult.put(AnaCons.ANA_CONS_SMA_PRE+period, df.get(df.length()-1,tecName));
      Boolean res = isBigDecimalOver(df,df.length()-1,tecName,df.length()-2,tecName);
      anaResult.put(key,getMark(res));
    }

    String[][] crossPeriods = {{"8","17"},{"8","25"},{"17","25"},{"25","99"}};

    for(String[] periods: crossPeriods){
      String shortP = periods[0];
      String longP = periods[1];
      String tecNameShortP = "sma"+shortP;
      String tecNameLongP = "sma"+longP;

      Boolean isShortOverLongPre =  isBigDecimalOver(df, df.length()-2 , tecNameShortP,df.length()-2, tecNameLongP);
      Boolean isShortOverLong =  isBigDecimalOver(df, df.length()-1 , tecNameShortP,df.length()-1, tecNameLongP );

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
    xAnaTagDao.save(tsCode,tradeDate,anaResult);
  }

  public Boolean isBigDecimalOver(DataFrame df, int row1,String col1, int row2,String col2){
    BigDecimal v1 = (BigDecimal)df.get(row1,col1);
    if(v1 == null){
      return null;
    }
    BigDecimal v2 = (BigDecimal)df.get(row2,col2);
    if(v2 == null)
    {
      return null;
    }
    return  v1.compareTo(v2) > 0;
  }


  public DataFrame getDataFrame(String tsCode,Date gteDate) {
    Result<List<SplitAdjustedDaily>> result  = splitAdjustService.getSplitAdjustDailyList(tsCode, gteDate);
    if(!result.isSuccess()){
      log.error(result.getErrorMsg());
      return null;
    }
    DataFrame df = convToDF(result.getData());
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
}