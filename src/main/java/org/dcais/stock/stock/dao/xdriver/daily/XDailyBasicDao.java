package org.dcais.stock.stock.dao.xdriver.daily;

import com.mysql.cj.xdevapi.Collection;
import com.mysql.cj.xdevapi.DbDoc;
import com.mysql.cj.xdevapi.DocResult;
import lombok.Getter;
import org.dcais.stock.stock.common.utils.JsonUtil;
import org.dcais.stock.stock.dao.xdriver.common.CollectionIndexDef;
import org.dcais.stock.stock.dao.xdriver.common.CollectionIndexInfo;
import org.dcais.stock.stock.dao.xdriver.common.CollectionIndexInterface;
import org.dcais.stock.stock.dao.xdriver.common.XCommon;
import org.dcais.stock.stock.entity.info.DailyBasic;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class XDailyBasicDao extends XCommon {
  @Getter
  private String collName = "stock_x_daily_basic";

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
  public DailyBasic getLatest(String tsCode) {
    Collection col = getCollection();
    DocResult docs = col.find("tsCode=:tsCode").sort("tradeDate desc").limit(1).bind("tsCode",tsCode).execute();
    if(!docs.hasData()){
      return null;
    }
    DbDoc dbDoc = docs.fetchOne();
    if(dbDoc == null ){
      return null;
    }
    DailyBasic dailyBasic = JsonUtil.getGsonObj().fromJson( dbDoc.toString(), DailyBasic.class );
    return dailyBasic;
  }
  public void insertList(List<DailyBasic> dailyList) {
    String[] arrayDaily = dailyList.stream().map(
      JsonUtil::toJson
    ).toArray(String[]::new);
    Collection col = getCollection();
    col.add(arrayDaily).execute();
  }

  public void deleteGte(String tsCode, Date gteTradeDate){
    Collection col = getCollection();
    Map<String,Object> params = new HashMap<>();
    params.put("tsCode",tsCode);
    params.put("gteTradeDate", gteTradeDate);
    col.remove("tsCode=:tsCode and tradeDate >= :gteTradeDate").bind(params).execute();
  }
  public void deleteGte(Date gteTradeDate){
    Collection col = getCollection();
    Map<String,Object> params = new HashMap<>();
    params.put("gteTradeDate", gteTradeDate);
    col.remove("tradeDate >= :gteTradeDate").bind(params).execute();
  }

}
