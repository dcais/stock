package org.dcais.stock.stock.http.tushare;

import com.github.dcais.aggra.annotation.HttpAttr;
import com.github.dcais.aggra.annotation.HttpRequest;
import com.github.dcais.aggra.annotation.ReqBody;
import com.github.dcais.aggra.enu.HttpMethod;
import org.dcais.stock.stock.http.tushare.param.TushareParam;

@HttpRequest(url = "${tushare.url}")
public interface TushareRequestApi {

  @HttpAttr(url = "/", method = HttpMethod.POST)
  String request(@ReqBody TushareParam tushareParam);
}
