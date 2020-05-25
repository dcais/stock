package org.dcais.stock.stock.biz.tec.impl;

import joinery.DataFrame;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.info.ISplitAdjustedDailyService;
import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.common.utils.DateUtils;
import org.dcais.stock.stock.common.utils.ListUtil;
import org.dcais.stock.stock.dao.xdriver.tec.XTecBaseDao;
import org.dcais.stock.stock.entity.info.SplitAdjustedDaily;
import org.dcais.stock.stock.entity.tec.TecMa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public abstract class BaseTaService {
  @Autowired
  private ISplitAdjustedDailyService splitAdjustedDailyService;
  @Getter
  @Value("${stock.batch-insert-size:1000}")
  private Integer batchInsertSize;
  @Autowired
  protected Date tecGteDate;

  List<BigDecimal> convertOutRealToList(double[] real, int begIdx, int outNBElement) {
    List<BigDecimal> list = new ArrayList<>(outNBElement + begIdx);
    for(int i = 0 ; i < begIdx + outNBElement ; i++ ){
      int idx = i - begIdx;
      if(idx < 0 ){
        list.add(null);
      }else {
        list.add(new BigDecimal(real[idx]));
      }
    }
    return list;
  }

  public DataFrame getDataFrame(String tsCode, Date gteDate) {
    Result<List<SplitAdjustedDaily>> result  = splitAdjustedDailyService.getSplitAdjustDailyList(tsCode, gteDate,null);
    if(!result.isSuccess()){
      log.error(result.getErrorMsg());
      return null;
    }
    DataFrame df = convToDF(result.getData());

    List<Date> tradeDates = df.col("tradeDate");
    List<String> strTradeDates = tradeDates.stream().map(t-> DateUtils.formatDate(t,DateUtils.YMD)).collect(Collectors.toList());
    df.add("strTradeDate",strTradeDates);

    Set columns = df.columns();

    DataFrame dfr = df.reindex( columns.size()-1, true);
    return  dfr;
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

  public void save (List<TecMa> mas, XTecBaseDao xTecBaseDao){
    if(ListUtil.isBlank(mas)){
      return;
    }
    List<List<TecMa>> masSubs = ListUtil.getSubDepartList(mas,getBatchInsertSize());
    for(List<TecMa> sub : masSubs){
      xTecBaseDao.insertList(sub);
    }
  }

  public Date getCheckEqualgtDate(){
    Date now = new Date();
    Calendar ca = Calendar.getInstance();
    ca.setTime(now);
    ca.add(Calendar.MONTH,-2);
    return ca.getTime();
  }

}
