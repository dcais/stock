package org.dcais.stock.stock.biz.ana;

import org.apache.tomcat.jni.Local;
import org.dcais.stock.stock.biz.IBaseService;
import org.dcais.stock.stock.entity.ana.DailyAnaTag;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 分析打标 服务类
 * </p>
 *
 * @author david-cai
 * @since 2020-06-06
 */
public interface IDailyAnaTagService extends IBaseService<DailyAnaTag> {
  Map ana(String tsCode, LocalDateTime tradeDate);

  void anaCompare(LocalDateTime tradeDate, List<Map> items);

  void anaRS50(LocalDateTime tradeDate, List<Map> items);
}
