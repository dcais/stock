package org.dcais.stock.stock.dao.xdriver.daily;

import com.mysql.cj.xdevapi.Collection;
import lombok.Getter;
import org.dcais.stock.stock.common.utils.JsonUtil;
import org.dcais.stock.stock.dao.xdriver.common.CollectionIndexDef;
import org.dcais.stock.stock.dao.xdriver.common.CollectionIndexInfo;
import org.dcais.stock.stock.dao.xdriver.common.CollectionIndexInterface;
import org.dcais.stock.stock.dao.xdriver.common.XCommon;
import org.dcais.stock.stock.entity.info.SplitAdjustedDaily;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class XSplitAdjustedDailyDao extends XCommon {
  @Getter
  private String collName = "stock_x_split_adjusted_daily";

  @Override
  public CollectionIndexInterface getCollectionIndexInterface() {
    return new CollectionIndexInterface() {
      @Override
      public List<CollectionIndexInfo> indexInfo() {
        List<CollectionIndexInfo> collectionIndexInfos = new ArrayList<>();

        CollectionIndexInfo collectionIndexInfo = new CollectionIndexInfo();
        collectionIndexInfo.setIndexName("idx_ts_code");
        collectionIndexInfo.setFields(new ArrayList<>());
        collectionIndexInfo.getFields().add(new CollectionIndexDef("$.tsCode", "TEXT(16)"));
        collectionIndexInfos.add(collectionIndexInfo);

        collectionIndexInfo = new CollectionIndexInfo();
        collectionIndexInfo.setIndexName("idx_trade_date");
        collectionIndexInfo.setFields(new ArrayList<>());
        collectionIndexInfo.getFields().add(new CollectionIndexDef("$.tradeDate", "TEXT(40)"));
        collectionIndexInfos.add(collectionIndexInfo);

        return collectionIndexInfos;
      }
    };
  }

  public void insertList(List<SplitAdjustedDaily> dailyList) {
    String[] arrayDaily = dailyList.stream().map(
      JsonUtil::toJson
    ).toArray(String[]::new);
    Collection col = getCollection();
    col.add(arrayDaily);
  }

  public void remove(String tsCode) {
  }


}
