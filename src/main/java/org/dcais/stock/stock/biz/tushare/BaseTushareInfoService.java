package org.dcais.stock.stock.biz.tushare;

import com.google.common.util.concurrent.RateLimiter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.text.StringEscapeUtils;
import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.common.utils.JsonUtil;
import org.dcais.stock.stock.http.tushare.TushareRequestApi;
import org.dcais.stock.stock.http.tushare.param.TushareParam;
import org.dcais.stock.stock.http.tushare.result.TushareData;
import org.dcais.stock.stock.http.tushare.result.TushareResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaseTushareInfoService {
  @Autowired
  protected TushareRequestApi tushareRequestApi;

  @Autowired
  protected TushareParamGem tushareParamGem;

  protected RateLimiter rateLimiter;

  public RateLimiter getRateLimiter(){
    return this.rateLimiter;
  }

  protected Result<TushareData> request(TushareParam tushareParam) {
    getRateLimiter().acquire();
    String basic = tushareRequestApi.request(tushareParam);
    basic = StringEscapeUtils.unescapeJava(basic);
    Gson gson = JsonUtil.getGsonObj();
    TushareResult tushareResult = gson.fromJson(basic, new TypeToken<TushareResult>() {
    }.getType());
    if (tushareResult.getCode() != 0) {
      return Result.wrapErrorResult("", tushareResult.getMsg());
    }
    return Result.wrapSuccessfulResult(tushareResult.getData());
  }

}
