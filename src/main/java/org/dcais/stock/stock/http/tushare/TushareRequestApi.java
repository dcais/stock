package org.dcais.stock.stock.http.tushare;

import com.yuanpin.common.http.annotation.HttpAttr;
import com.yuanpin.common.http.annotation.HttpRequest;
import com.yuanpin.common.http.annotation.ReqBody;
import com.yuanpin.common.http.enu.HttpMehod;
import org.dcais.stock.stock.http.tushare.param.TushareParam;

@HttpRequest(url = "${tushare.url}")
public interface TushareRequestApi {

    @HttpAttr(url="/",method = HttpMehod.POST)
    String request(@ReqBody TushareParam tushareParam);
}
