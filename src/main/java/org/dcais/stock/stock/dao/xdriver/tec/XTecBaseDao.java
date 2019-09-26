package org.dcais.stock.stock.dao.xdriver.tec;

import com.google.common.reflect.TypeToken;
import com.mysql.cj.xdevapi.Collection;
import com.mysql.cj.xdevapi.DocResult;
import org.dcais.stock.stock.common.utils.DateUtils;
import org.dcais.stock.stock.common.utils.JsonUtil;
import org.dcais.stock.stock.dao.xdriver.common.CollectionIndexDef;
import org.dcais.stock.stock.dao.xdriver.common.CollectionIndexInfo;
import org.dcais.stock.stock.dao.xdriver.common.CollectionIndexInterface;
import org.dcais.stock.stock.dao.xdriver.common.XCommon;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

import static com.google.common.base.Preconditions.checkArgument;

@Component
public abstract class XTecBaseDao<T> extends XCommon {

  private final Class<T> type;

  @SuppressWarnings("unchecked")
  protected XTecBaseDao() {
    Type superclass = getClass().getGenericSuperclass();
    checkArgument(superclass instanceof ParameterizedType,
      "%s isn't parameterized", superclass);
    Type runtimeType = ((ParameterizedType) superclass).getActualTypeArguments()[0];
    type = (Class<T>) TypeToken.of(runtimeType).getRawType();
  }

  @Override
  public CollectionIndexInterface getCollectionIndexInterface() {
    return new CollectionIndexInterface() {
      @Override
      public List<CollectionIndexInfo> indexInfo() {
        List<CollectionIndexInfo> collectionIndexInfos = new ArrayList<>();

        CollectionIndexInfo collectionIndexInfo = new CollectionIndexInfo();
//        collectionIndexInfo.setIndexName("idx_ts_code");
//        collectionIndexInfo.setFields(new ArrayList<>());
//        collectionIndexInfo.getFields().add(new CollectionIndexDef("$.tsCode", "TEXT(16)"));
//        collectionIndexInfos.add(collectionIndexInfo);
//
//        collectionIndexInfo = new CollectionIndexInfo();
//        collectionIndexInfo.setIndexName("idx_trade_date");
//        collectionIndexInfo.setFields(new ArrayList<>());
//        collectionIndexInfo.getFields().add(new CollectionIndexDef("$.tradeDate", "TEXT(40)"));
//        collectionIndexInfos.add(collectionIndexInfo);
//
//        collectionIndexInfo = new CollectionIndexInfo();
//        collectionIndexInfo.setIndexName("idx_tecName");
//        collectionIndexInfo.setFields(new ArrayList<>());
//        collectionIndexInfo.getFields().add(new CollectionIndexDef("$.tecName", "TEXT(40)"));
//        collectionIndexInfos.add(collectionIndexInfo);

        collectionIndexInfo = new CollectionIndexInfo();
        collectionIndexInfo.setIndexName("idx_tscode_tecname_tradedate");
        collectionIndexInfo.setFields(new ArrayList<>());
        collectionIndexInfo.getFields().add(new CollectionIndexDef("$.tsCode", "TEXT(16)"));
        collectionIndexInfo.getFields().add(new CollectionIndexDef("$.tecName", "TEXT(40)"));
        collectionIndexInfo.getFields().add(new CollectionIndexDef("$.tradeDate", "TEXT(40)"));
        collectionIndexInfos.add(collectionIndexInfo);
        return collectionIndexInfos;
      }
    };
  }

  public void insertList(List<T> tecs) {
    String[] arrayDaily = tecs.stream().map(
      JsonUtil::toJson
    ).toArray(String[]::new);
    Collection col = getCollection();
    col.add(arrayDaily).execute();
  }

  public void remove(String tsCode) {
    Collection col = getCollection();
    col.remove("tsCode=:tsCode").bind("tsCode",tsCode).execute();
  }
  public void removeAll(){
    Collection col = getCollection();
    col.remove("true").execute();
  }

  public List<T> getFromDate(String tsCode, String key, Date gteDate){
    String strDate = DateUtils.formatDate(gteDate,DateUtils.ISO_DATE_TIME);
    Collection col = getCollection();
    Map<String,Object> param = new HashMap<>();
    param.put("tsCode",tsCode);
    param.put("gteTradeDate", gteDate);
    param.put("tecName", key);
    DocResult docs = col.find("tsCode=:tsCode AND tradeDate>=:gteTradeDate AND tecName=:tecName").sort("tradeDate").bind(param).execute();
    if(!docs.hasData()){
      return new ArrayList<>();
    }
    List<T> results = new LinkedList<>();
    docs.forEach(dbDoc -> {
      T data = JsonUtil.getGsonObj().fromJson( dbDoc.toString(), this.type);
      results.add(data);
    });
    return results;
  }

}
