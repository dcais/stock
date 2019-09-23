package org.dcais.stock.stock.biz.tec.impl;

import com.tictactec.ta.lib.CoreAnnotated;
import com.tictactec.ta.lib.MAType;
import com.tictactec.ta.lib.MInteger;
import joinery.DataFrame;
import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.biz.info.SplitAdjustService;
import org.dcais.stock.stock.biz.tec.MAService;
import org.dcais.stock.stock.common.utils.ListUtil;
import org.dcais.stock.stock.dao.xdriver.tec.XSMADao;
import org.dcais.stock.stock.dao.xdriver.tec.XTecBaseDao;
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
public class MAServiceImpl extends BaseTaService implements MAService {
  @Autowired
  private SplitAdjustService splitAdjustService;
  @Autowired
  private XSMADao xsmaDao;

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

  protected String getTecName(MAType maType, Integer period){
    return maType.name() + period;
  }

  protected void calc(String tsCode, MAType maType, List<Integer> periods){
    XTecBaseDao xTecBaseDao;
    if(ListUtil.isBlank(periods)){
      String errMsg = "periods is empty";
      log.error(errMsg);
      throw  new RuntimeException(errMsg);
    }
    if(maType == MAType.Sma){
       xTecBaseDao = xsmaDao;
    }else{
      log.error("Unknown MAType"+maType.name());
      throw new RuntimeException("Unknown MAType "+maType.name());
    }

    DataFrame df =  getDataFrame(tsCode);
    if(df == null){
      log.error("cannot get dataFrame. [tsCode]"+ tsCode);
      return;
    }

    periods.forEach( period ->{
      calc( df,maType,period);
    });

    xTecBaseDao.remove(tsCode);
    periods.forEach( period ->{
      String colName = getTecName(maType,period);
      List<TecMa> mas = new LinkedList<>();
      for (int row=0 ; row< df.length() ; row ++ ){
        TecMa tecMa = new TecMa();
        tecMa.setTsCode((String) df.get(row,"tsCode"));
        tecMa.setTecName(colName);
        tecMa.setTradeDate((Date) df.get(row,"tradeDate"));
        tecMa.setValue((BigDecimal) df.get(row,colName));
        mas.add(tecMa);
      }
      this.save(mas,xTecBaseDao);
    });
  }

  @Override
  public void calcSMA(String tsCode, List<Integer> periods) {
    this.calc(tsCode,MAType.Sma, periods);
  }
}
