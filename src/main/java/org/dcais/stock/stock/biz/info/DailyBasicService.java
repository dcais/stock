package org.dcais.stock.stock.biz.info;

import org.dcais.stock.stock.common.result.Result;
import org.dcais.stock.stock.entity.info.Daily;

import java.util.List;
import java.util.Map;


public interface DailyBasicService {

  void syncAll(String mode);

  Result syncHistory(String symbol);
}


