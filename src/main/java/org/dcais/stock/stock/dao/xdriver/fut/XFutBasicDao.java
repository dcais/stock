package org.dcais.stock.stock.dao.xdriver.fut;

import com.mysql.cj.xdevapi.Collection;
import com.mysql.cj.xdevapi.DbDoc;
import com.mysql.cj.xdevapi.DocResult;
import lombok.Getter;
import org.dcais.stock.stock.common.utils.DateUtils;
import org.dcais.stock.stock.common.utils.JsonUtil;
import org.dcais.stock.stock.dao.xdriver.common.CollectionIndexDef;
import org.dcais.stock.stock.dao.xdriver.common.CollectionIndexInfo;
import org.dcais.stock.stock.dao.xdriver.common.CollectionIndexInterface;
import org.dcais.stock.stock.dao.xdriver.common.XCommon;
import org.dcais.stock.stock.entity.basic.FutBasic;
import org.dcais.stock.stock.entity.info.FinIncome;
import org.dcais.stock.stock.entity.info.SplitAdjustedDaily;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class XFutBasicDao extends XCommon {
  @Getter
  private String collName = "future_x_basic";

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
        collectionIndexInfo.setIndexName("idx_symbol");
        collectionIndexInfo.setFields(new ArrayList<>());
        collectionIndexInfo.getFields().add(new CollectionIndexDef("$.symbol", "TEXT(16)"));
        collectionIndexInfos.add(collectionIndexInfo);

        collectionIndexInfo = new CollectionIndexInfo();
        collectionIndexInfo.setIndexName("idx_fut_code");
        collectionIndexInfo.setFields(new ArrayList<>());
        collectionIndexInfo.getFields().add(new CollectionIndexDef("$.futCode", "TEXT(40)"));
        collectionIndexInfos.add(collectionIndexInfo);

        collectionIndexInfo = new CollectionIndexInfo();
        collectionIndexInfo.setIndexName("idx_exchange");
        collectionIndexInfo.setFields(new ArrayList<>());
        collectionIndexInfo.getFields().add(new CollectionIndexDef("$.exchange", "TEXT(40)"));
        collectionIndexInfos.add(collectionIndexInfo);
        return collectionIndexInfos;
      }
    };
  }

  public void insertList(List<FutBasic> futBasics) {
    String[] arrayDaily = futBasics.stream().map(
      JsonUtil::toJson
    ).toArray(String[]::new);
    Collection col = getCollection();
    col.add(arrayDaily).execute();
  }

  public void remove(String tsCode) {
    Collection col = getCollection();
    col.remove("tsCode=:tsCode").bind("tsCode",tsCode).execute();
  }

  public FutBasic get(String tsCode){
    Collection col = getCollection();
    Map<String, Object> params = new HashMap<>();
    params.put("tsCode",tsCode);
    DocResult docs = col.find("tsCode=:tsCode").bind(params).execute();
    if (!docs.hasData()) {
      return null;
    }
    DbDoc dbDoc = docs.fetchOne();
    if (dbDoc == null) {
      return null;
    }
    FutBasic futBasic = JsonUtil.getGsonObj().fromJson(dbDoc.toString(), FutBasic.class);
    return futBasic;
  }

  public List<FutBasic> getByExange(String exchange){
    Collection col = getCollection();
    Map<String, Object> params = new HashMap<>();
    params.put("exchange",exchange);
    DocResult docs = col.find("exchange=:exchange").bind(params).execute();
    if (!docs.hasData()) {
      return new ArrayList<>();
    }
    List<FutBasic> results = new LinkedList<>();
    docs.forEach(dbDoc -> {
      FutBasic  futBasic= JsonUtil.getGsonObj().fromJson( dbDoc.toString(), FutBasic.class );
      results.add(futBasic);
    });
    return results;
  }

}
