package org.dcais.stock.stock.biz.tushare;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.dcais.stock.stock.biz.tushare.parser.TushareDataParser;
import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.common.utils.JsonUtil;
import org.dcais.stock.stock.common.utils.StringUtil;
import org.dcais.stock.stock.entity.basic.Basic;
import org.dcais.stock.stock.http.tushare.TushareRequestApi;
import org.dcais.stock.stock.http.tushare.param.TushareParam;
import org.dcais.stock.stock.http.tushare.result.TushareData;
import org.dcais.stock.stock.http.tushare.result.TushareResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class StockInfoService {
    @Autowired
    private TushareRequestApi tushareRequestApi;

    @Autowired
    private TushareParamGem tushareParamGem;

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

    public Result<TushareData> request(TushareParam tushareParam){
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
