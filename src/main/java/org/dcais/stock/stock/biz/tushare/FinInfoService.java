package org.dcais.stock.stock.biz.tushare;

import com.google.common.util.concurrent.RateLimiter;
import org.dcais.stock.stock.biz.tushare.parser.TushareDataParser;
import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.entity.info.FinIncome;
import org.dcais.stock.stock.http.tushare.param.TushareParam;
import org.dcais.stock.stock.http.tushare.result.TushareData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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
    rateLimiter = RateLimiter.create(1);
  }

  @Override
  public RateLimiter getRateLimiter(){
    return this.rateLimiter;
  }

  public Result finIncome(String tsCode) {
    TushareParam tushareParam = tushareParamGem.getParam("income");
    Map<String, Object> param = new HashMap<>();
    param.put("ts_code", tsCode);

    tushareParam.setParams(param);
    Result<TushareData> tushareResult = this.request(tushareParam);
    if (!tushareResult.isSuccess()) {
      return tushareResult;
    }
    List<FinIncome> finIncomes = TushareDataParser.parse(tushareResult.getData(), FinIncome.class);
    return Result.wrapSuccessfulResult(finIncomes);
  }

}
