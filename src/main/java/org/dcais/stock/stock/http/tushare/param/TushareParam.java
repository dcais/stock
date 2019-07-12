package org.dcais.stock.stock.http.tushare.param;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class TushareParam {
    @SerializedName("api_name")
    protected String apiName;
    protected String token;
    protected Object params;
    protected String[] fields;
}
