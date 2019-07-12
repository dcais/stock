package org.dcais.stock.stock.http.tushare.result;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class TushareResult {

    @SerializedName("request_id")
    private String requestId;
    private Integer code;
    private String msg;
    private TushareData data;

}
