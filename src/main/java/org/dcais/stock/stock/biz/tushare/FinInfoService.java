package org.dcais.stock.stock.biz.tushare;

import com.google.common.util.concurrent.RateLimiter;
import org.dcais.stock.stock.biz.tushare.parser.TushareDataParser;
import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.common.utils.DateUtils;
import org.dcais.stock.stock.common.utils.LocalDateUtils;
import org.dcais.stock.stock.common.utils.StringUtil;
import org.dcais.stock.stock.entity.info.FinIncome;
import org.dcais.stock.stock.entity.info.FinIndicator;
import org.dcais.stock.stock.http.tushare.param.TushareParam;
import org.dcais.stock.stock.http.tushare.result.TushareData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FinInfoService extends StockInfoService {
  private RateLimiter rateLimiter;

  @Autowired
  private TushareParamGem tushareParamGem;
  
  @PostConstruct
  private void init() {
    rateLimiter = RateLimiter.create(50);
  }

  @Override
  public RateLimiter getRateLimiter(){
    return this.rateLimiter;
  }

  public Result finIncome(String tsCode,Date startDate) {
    TushareParam tushareParam = tushareParamGem.getParam("income");
    Map<String, Object> param = new HashMap<>();
    param.put("ts_code", tsCode);
    if( startDate != null ){
      param.put("start_date", DateUtils.formatDate(startDate,DateUtils.YMD));
    }
    tushareParam.setParams(param);
    tushareParam.setFields(TushareRequestFields.fin_income);
    Result<TushareData> tushareResult = this.request(tushareParam);
    if (!tushareResult.isSuccess()) {
      return tushareResult;
    }
    List<FinIncome> finIncomes = TushareDataParser.parse(tushareResult.getData(), FinIncome.class);
    return Result.wrapSuccessfulResult(finIncomes);
  }

  public Result finIndicator(String tsCode, LocalDateTime startDate) {
    TushareParam tushareParam = tushareParamGem.getParam("fina_indicator_vip");
    Map<String, Object> param = new HashMap<>();
    if(StringUtil.isNotBlank(tsCode)){
      param.put("ts_code", tsCode);
    }
    if( startDate != null ){
      param.put("start_date", LocalDateUtils.formatToStr(startDate,DateUtils.YMD));
    }
    tushareParam.setParams(param);
    tushareParam.setFields(TushareRequestFields.fin_indicator);
    Result<TushareData> tushareResult = this.request(tushareParam);
    if (!tushareResult.isSuccess()) {
      return tushareResult;
    }
    List<FinIndicator> finIncomes = TushareDataParser.parse(tushareResult.getData(), FinIndicator.class);
    return Result.wrapSuccessfulResult(finIncomes);
  }

}
