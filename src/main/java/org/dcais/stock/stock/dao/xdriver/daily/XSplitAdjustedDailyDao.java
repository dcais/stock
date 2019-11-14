package org.dcais.stock.stock.dao.xdriver.daily;

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
import org.dcais.stock.stock.entity.info.SplitAdjustedDaily;
import org.springframework.stereotype.Component;

import java.util.*;

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
    col.add(arrayDaily).execute();
  }

  public void remove(String tsCode) {
    Collection col = getCollection();
    col.remove("tsCode=:tsCode").bind("tsCode",tsCode).execute();
  }

  public SplitAdjustedDaily getLatest(String tsCode) {
    Collection col = getCollection();
    DocResult docs = col.find("tsCode=:tsCode").sort("tradeDate desc").limit(1).bind("tsCode",tsCode).execute();
    if(!docs.hasData()){
      return null;
    }
    DbDoc dbDoc = docs.fetchOne();
    if(dbDoc == null ){
      return null;
    }
    SplitAdjustedDaily  splitAdjustedDaily = JsonUtil.getGsonObj().fromJson( dbDoc.toString(), SplitAdjustedDaily.class );
    return splitAdjustedDaily;
  }

  public List<SplitAdjustedDaily> getFromDate(String tsCode, Date gteDate, Date lteDate){
    String strDateGte= DateUtils.formatDate(gteDate,DateUtils.ISO_DATE_TIME);
    String strDateLte = null;
    if( lteDate != null ){
      strDateLte = DateUtils.formatDate(lteDate,DateUtils.ISO_DATE_TIME);
    }
    Collection col = getCollection();
    Map<String,Object> param = new HashMap<>();
    param.put("tsCode",tsCode);
    param.put("gteTradeDate", strDateGte);
    if( lteDate != null ){
      param.put("lteTradeDate", strDateLte);
    }
    String query = "tsCode=:tsCode AND tradeDate>=:gteTradeDate";
    if( lteDate != null ){
      query = query + " AND tradeDate <=:lteTradeDate";
    }
    DocResult docs = col.find(query).sort("tradeDate ").bind(param).execute();
    if(!docs.hasData()){
      return new ArrayList<>();
    }

    List<SplitAdjustedDaily> results = new LinkedList<>();
    docs.forEach(dbDoc -> {
      SplitAdjustedDaily  splitAdjustedDaily = JsonUtil.getGsonObj().fromJson( dbDoc.toString(), SplitAdjustedDaily.class );
      results.add(splitAdjustedDaily);
    });
    return results;
  }

}
