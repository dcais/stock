package org.dcais.stock.stock.common.utils;

import com.tictactec.ta.lib.CoreAnnotated;
import com.tictactec.ta.lib.MAType;
import com.tictactec.ta.lib.MInteger;
import joinery.DataFrame;
import org.dcais.stock.stock.entity.tec.TecMa;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TalibUtil {
  public static List<BigDecimal> movingAverage(List<BigDecimal> closes,MAType maType,int period){

    CoreAnnotated coreAnnotated = new CoreAnnotated();
    double[] arrClose =  new double[closes.size()];

    for(int i = 0 ; i < closes.size() ; i++ ){
      arrClose[i] = closes.get(i).doubleValue();
    }

    MInteger outBegIdx = new MInteger();
    MInteger outNBElement = new MInteger();
    double[] outReal = new double[arrClose.length];


    List<BigDecimal> ma = new ArrayList<>();
    if( period <= arrClose.length){
      coreAnnotated.movingAverage(0, arrClose.length-1, arrClose, period , maType, outBegIdx, outNBElement,outReal );
      ma = convertOutRealToList(outReal,outBegIdx.value,outNBElement.value);
    }else {
      for(int i = 0 ; i < arrClose.length ; i++){
        ma.add(null);
      }
    }
    return ma;
  }

  public static List<BigDecimal> sar (List<BigDecimal> highs,List<BigDecimal> lows , double acceleration , double max){
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
    coreAnnotated.sar(0, arrHigh.length-1, arrHigh,arrLow , acceleration, max, outBegIdx,outNBElement, outReal );

    List<BigDecimal> sars = convertOutRealToList(outReal,outBegIdx.value,outNBElement.value);
    return sars;
  }

  public static List<BigDecimal> convertOutRealToList(double[] real, int begIdx, int outNBElement) {
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
}
