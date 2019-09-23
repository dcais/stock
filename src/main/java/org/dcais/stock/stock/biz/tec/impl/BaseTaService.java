package org.dcais.stock.stock.biz.tec.impl;

import joinery.DataFrame;
import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.info.SplitAdjustService;
import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.dao.xdriver.tec.XSMADao;
import org.dcais.stock.stock.entity.info.SplitAdjustedDaily;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class BaseTaService {
  @Autowired
  private SplitAdjustService splitAdjustService;
  @Autowired
  private XSMADao xsmaDao;

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

  public DataFrame getDataFrame(String tsCode) {
    Result<List<SplitAdjustedDaily>> result  = splitAdjustService.getSplitAdjustDailyList(tsCode, null);
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
