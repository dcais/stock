package org.dcais.stock.stock.biz.tushare;

import com.google.common.util.concurrent.RateLimiter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.dcais.stock.stock.biz.tushare.parser.TushareDataParser;
import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.common.utils.DateUtils;
import org.dcais.stock.stock.common.utils.JsonUtil;
import org.dcais.stock.stock.common.utils.StringUtil;
import org.dcais.stock.stock.entity.basic.Basic;
import org.dcais.stock.stock.entity.basic.FutBasic;
import org.dcais.stock.stock.entity.basic.TradeCal;
import org.dcais.stock.stock.entity.info.AdjFactor;
import org.dcais.stock.stock.entity.info.Daily;
import org.dcais.stock.stock.entity.info.DailyBasic;
import org.dcais.stock.stock.http.tushare.TushareRequestApi;
import org.dcais.stock.stock.http.tushare.param.TushareParam;
import org.dcais.stock.stock.http.tushare.result.TushareData;
import org.dcais.stock.stock.http.tushare.result.TushareResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FutureInfoService extends BaseTushareInfoService {
  @PostConstruct
  private void init() {
    rateLimiter = RateLimiter.create(3);
  }

  public Result futBasic(String exchange){
    TushareParam tushareParam = tushareParamGem.getParam("fut_basic");
    tushareParam.setFields(TushareRequestFields.fut_basic);
    Map<String,Object> params = new HashMap<>();

    tushareParam.setParams(params);
    params.put("exchange",exchange);
    Result<TushareData> tushareResult = this.request(tushareParam);
    if (!tushareResult.isSuccess()) {
      return tushareResult;
    }

    List<FutBasic> basicList = TushareDataParser.parse(tushareResult.getData(), FutBasic.class);
    return Result.wrapSuccessfulResult(basicList);
  }

}
