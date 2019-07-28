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
import org.dcais.stock.stock.entity.info.AdjFactor;
import org.dcais.stock.stock.entity.info.Daily;
import org.dcais.stock.stock.entity.basic.TradeCal;
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
public class StockInfoService {
    @Autowired
    private TushareRequestApi tushareRequestApi;

    @Autowired
    private TushareParamGem tushareParamGem;

    private RateLimiter rateLimiter;

    @PostConstruct
    private void init(){
        rateLimiter = RateLimiter.create(3);
    }

    public Result stockBasicInfo(String listStatus){
        TushareParam tushareParam = tushareParamGem.getParam("stock_basic");
        tushareParam.setFields(TushareRequestFields.basic);

        if(StringUtils.isNotBlank(listStatus)){
            Map<String,Object> param = new HashMap<>();
            param.put("list_status", listStatus);
            tushareParam.setParams(param);
        }

        Result<TushareData> tushareResult = this.request(tushareParam);
        if(!tushareResult.isSuccess()){
            return tushareResult;
        }

        List<Basic> basicList = TushareDataParser.parse(tushareResult.getData(), Basic.class);
        return Result.wrapSuccessfulResult(basicList);
    }

    public Result tradeCal(String exchange, Date startDate, Date endDate){
        TushareParam tushareParam = tushareParamGem.getParam("trade_cal");
        tushareParam.setFields(TushareRequestFields.tradeCal);
        Map<String,Object> param = new HashMap<>();
        if(StringUtil.isNotBlank(exchange)){
            param.put("exchange", exchange);
        }
        if(startDate != null ){
            param.put("start_date", DateUtils.formatDate(startDate,DateUtils.YMD));
        }
        if(endDate!= null ){
            param.put("end_date", DateUtils.formatDate(endDate,DateUtils.YMD));
        }
        tushareParam.setParams(param);
        Result<TushareData> tushareResult = this.request(tushareParam);
        if(!tushareResult.isSuccess()){
            return tushareResult;
        }
        List<TradeCal> tradeCals = TushareDataParser.parse(tushareResult.getData(), TradeCal.class);
        return Result.wrapSuccessfulResult(tradeCals);
    }

    public  Result daily(String tsCode , Date tradeDate , Date startDate, Date endDate){
        TushareParam tushareParam = tushareParamGem.getParam("daily");
        tushareParam.setFields(TushareRequestFields.daily);
        Map<String,Object> param = new HashMap<>();

        if(StringUtil.isNotBlank(tsCode)){
            param.put("ts_code",tsCode);
        }
        if(tradeDate != null){
            param.put("trade_date",DateUtils.formatDate(tradeDate,DateUtils.YMD));
        }
        if(startDate != null ){
            param.put("start_date", DateUtils.formatDate(startDate,DateUtils.YMD));
        }
        if(endDate != null ){
            param.put("end_date", DateUtils.formatDate(endDate,DateUtils.YMD));
        }
        tushareParam.setParams(param);
        Result<TushareData> tushareResult = this.request(tushareParam);
        if(!tushareResult.isSuccess()){
            return tushareResult;
        }

        List<Daily> daily = TushareDataParser.parse(tushareResult.getData(), Daily.class);
        return Result.wrapSuccessfulResult(daily);
    }

    public Result adjFactor (String tsCode , Date tradeDate , Date startDate, Date endDate){
        TushareParam tushareParam = tushareParamGem.getParam("adj_factor");
        tushareParam.setFields(TushareRequestFields.adjFactor);
        Map<String,Object> param = new HashMap<>();

        if(StringUtil.isNotBlank(tsCode)){
            param.put("ts_code",tsCode);
        }
        if(tradeDate != null){
            param.put("trade_date",DateUtils.formatDate(tradeDate,DateUtils.YMD));
        }
        if(startDate != null ){
            param.put("start_date", DateUtils.formatDate(startDate,DateUtils.YMD));
        }
        if(endDate != null ){
            param.put("end_date", DateUtils.formatDate(endDate,DateUtils.YMD));
        }
        tushareParam.setParams(param);
        Result<TushareData> tushareResult = this.request(tushareParam);
        if(!tushareResult.isSuccess()){
            return tushareResult;
        }

        List<AdjFactor> daily = TushareDataParser.parse(tushareResult.getData(), AdjFactor.class);
        return Result.wrapSuccessfulResult(daily);
    }

    private Result<TushareData> request(TushareParam tushareParam){
        rateLimiter.acquire();
        String basic = tushareRequestApi.request(tushareParam);
        basic = StringEscapeUtils.unescapeJava(basic);
        Gson gson = JsonUtil.getGsonObj();
        TushareResult tushareResult = gson.fromJson(basic,new TypeToken<TushareResult>(){}.getType());
        if(tushareResult.getCode() != 0 ){
            return Result.wrapErrorResult("",tushareResult.getMsg());
        }
        return Result.wrapSuccessfulResult(tushareResult.getData());
    }
}
