package org.dcais.stock.stock.biz.tushare;

import org.dcais.stock.stock.http.tushare.param.TushareParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class TushareParamGem {
    @Value("${tushare.token}")
    private String token;

    public TushareParam getParam(String apiName){
        TushareParam param = new TushareParam();
        param.setApiName(apiName);
        param.setToken(token);
        param.setParams( new HashMap<String,Object>());
        return param;
    }
}
