package org.dcais.stock.stock.biz.tec.impl;

import com.tictactec.ta.lib.CoreAnnotated;
import com.tictactec.ta.lib.MAType;
import com.tictactec.ta.lib.MInteger;
import joinery.DataFrame;
import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.info.SplitAdjustService;
import org.dcais.stock.stock.biz.tec.MAService;
import org.dcais.stock.stock.biz.tec.SARService;
import org.dcais.stock.stock.common.utils.DateUtils;
import org.dcais.stock.stock.common.utils.ListUtil;
import org.dcais.stock.stock.dao.xdriver.tec.XSMADao;
import org.dcais.stock.stock.dao.xdriver.tec.XTecBaseDao;
import org.dcais.stock.stock.dao.xdriver.tec.XTecSARDao;
import org.dcais.stock.stock.entity.tec.TecMa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
@Slf4j
public class SARServiceImpl extends BaseTaService implements SARService {
  @Autowired
  private SplitAdjustService splitAdjustService;
  @Autowired
  private XTecSARDao xTecSARDao;

  public void calc(DataFrame df, MAType maType,Integer period){

    CoreAnnotated coreAnnotated = new CoreAnnotated();

    double[] arrClose =  new double[df.length()];

    List<BigDecimal> closes =  df.col("close");
    for(int i = 0 ; i < closes.size() ; i++ ){
      arrClose[i] = closes.get(i).doubleValue();
    }

    MInteger outBegIdx = new MInteger();
    MInteger outNBElement = new MInteger();
    double[] outReal = new double[arrClose.length];


    List<BigDecimal> ma = new ArrayList<>();
    if( period <= arrClose.length){
      coreAnnotated.movingAverage(0, arrClose.length-1, arrClose, period , MAType.Sma, outBegIdx, outNBElement,outReal );
      ma = convertOutRealToList(outReal,outBegIdx.value,outNBElement.value);
    }else {
      for(int i = 0 ; i < arrClose.length ; i++){
        ma.add(null);
      }
    }

    df.add(getTecName(maType,period),ma);
  }
  public String getTecName(){
    return "sar";
  }

  protected String getTecName(MAType maType, Integer period){
    return maType.name() + period;
  }

  public void calc(String tsCode){
    XTecBaseDao xTecBaseDao;
    xTecBaseDao = xTecSARDao;

    DataFrame df =  getDataFrame(tsCode,tecGteDate);
    if(df == null){
      log.error("cannot get dataFrame. [tsCode]"+ tsCode);
      return;
    }

    List<BigDecimal> highs =  df.col("high");
    List<BigDecimal> lows =  df.col("low");
    double[] arrHigh = new double[highs.size()];
    double[] arrLow = new double[lows.size()];
    for(int i = 0 ; i < highs.size() ; i++ ){
      arrHigh[i] = highs.get(i).doubleValue();
    }
    for(int i = 0 ; i < lows.size() ; i++ ){
      arrLow[i] = lows.get(i).doubleValue();
    }

    MInteger outBegIdx = new MInteger();
    MInteger outNBElement = new MInteger();
    double[] outReal = new double[arrHigh.length];

    CoreAnnotated coreAnnotated = new CoreAnnotated();
    coreAnnotated.sar(0, arrHigh.length-1, arrHigh,arrLow , 0.02d, 0.2d, outBegIdx,outNBElement, outReal );

    List<BigDecimal> sars = convertOutRealToList(outReal,outBegIdx.value,outNBElement.value);
    df.add(getTecName(),sars);

    List<TecMa> list = xTecBaseDao.getFromDate(tsCode,getTecName() ,getCheckEqualgtDate());
    TecMa lastInDb = null;
    if(ListUtil.isNotBlank(list)){
      lastInDb = list.get(list.size()-1);
    }

    DataFrame dfToBeSave = df;
    if(lastInDb != null ){

      String strLastInDbTradeDate = DateUtils.formatDate(lastInDb.getTradeDate(),DateUtils.YMD);
      BigDecimal tecValue = (BigDecimal) df.get(strLastInDbTradeDate,getTecName());

      if(tecValue.setScale(2,BigDecimal.ROUND_FLOOR).compareTo(lastInDb.getValue().setScale(2,BigDecimal.ROUND_FLOOR)) != 0 ){
        xTecBaseDao.remove(tsCode);
      } else {
        Date gtSlice = lastInDb.getTradeDate();
        dfToBeSave= df.select((DataFrame.Predicate<Object>) value -> {
          Date tradeDate = (Date) value.get(1);
          return tradeDate.compareTo(gtSlice) > 0;
        });
      }
    }else {
      xTecBaseDao.remove(tsCode);
    }

    String colName = getTecName();
    List<TecMa> mas = new LinkedList<>();

    dfToBeSave = dfToBeSave.resetIndex();
    for (int row=0 ; row< dfToBeSave.length() ; row ++ ){
      TecMa tecMa = new TecMa();
      tecMa.setTsCode((String) dfToBeSave.get(row,"tsCode"));
      tecMa.setTecName(colName);
      tecMa.setTradeDate((Date) dfToBeSave.get(row,"tradeDate"));
      tecMa.setValue((BigDecimal) dfToBeSave.get(row,colName));
      mas.add(tecMa);
    }
    this.save(mas,xTecBaseDao);
  }
  @Override
  public void removeAll() {
    xTecSARDao.removeAll();
  }
}
