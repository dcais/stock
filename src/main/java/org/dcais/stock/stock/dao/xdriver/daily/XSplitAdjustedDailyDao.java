package org.dcais.stock.stock.dao.xdriver.daily;

import com.mysql.cj.xdevapi.Collection;
import lombok.Getter;
import org.dcais.stock.stock.common.utils.JsonUtil;
import org.dcais.stock.stock.dao.xdriver.common.XCommonCollDao;
import org.dcais.stock.stock.entity.info.SplitAdjustedDaily;

import java.util.List;

public class XSplitAdjustedDailyDao extends XCommonCollDao {
  @Getter
  private String collName = "stock_x_split_adjusted_daily";

  public void insertList(List<SplitAdjustedDaily> dailyList) {
    String[] arrayDaily = dailyList.stream().map(
      JsonUtil::toJson
    ).toArray(String[]::new);
    Collection coll = this.getCollection();
    coll.getSession().close();
  }


}
