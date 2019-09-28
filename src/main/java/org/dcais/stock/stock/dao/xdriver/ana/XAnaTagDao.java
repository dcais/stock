package org.dcais.stock.stock.dao.xdriver.ana;

import com.mysql.cj.xdevapi.Collection;
import lombok.Getter;
import org.dcais.stock.stock.common.utils.DateUtils;
import org.dcais.stock.stock.common.utils.JsonUtil;
import org.dcais.stock.stock.dao.xdriver.common.CollectionIndexDef;
import org.dcais.stock.stock.dao.xdriver.common.CollectionIndexInfo;
import org.dcais.stock.stock.dao.xdriver.common.CollectionIndexInterface;
import org.dcais.stock.stock.dao.xdriver.common.XCommon;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class XAnaTagDao extends XCommon {
  @Getter
  private String collName = "stock_x_ana_tag";

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

        collectionIndexInfo = new CollectionIndexInfo();
        collectionIndexInfo.setIndexName("idx_tscode_tradedate");
        collectionIndexInfo.setFields(new ArrayList<>());
        collectionIndexInfo.getFields().add(new CollectionIndexDef("$.tsCode", "TEXT(16)"));
        collectionIndexInfo.getFields().add(new CollectionIndexDef("$.tradeDate", "TEXT(40)"));
        collectionIndexInfos.add(collectionIndexInfo);
        return collectionIndexInfos;
      }
    };
  }

  public void remove(String tsCode ,Date tradeDate){
    Collection col = this.getCollection();
    Map<String,Object> params = new HashMap<>();
    params.put("tsCode",tsCode);
    params.put("tradeDate", DateUtils.formatDate(tradeDate,DateUtils.ISO_DATE_TIME));
    col.remove("tsCode = :tsCode AND tradeDate = :tradeDate").bind(params).execute();
  }

  public void save(String tsCode, Date tradeDate, Map<String,Object> tags){
    Collection col = this.getCollection();
    tags.put("tsCode",tsCode);
    tags.put("tradeDate",tradeDate);

    String json = JsonUtil.toJson(tags);
    col.add(json).execute();
  }
}
