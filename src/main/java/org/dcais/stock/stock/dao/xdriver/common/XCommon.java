package org.dcais.stock.stock.dao.xdriver.common;

import com.mysql.cj.xdevapi.*;
import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.common.utils.JsonUtil;
import org.dcais.stock.stock.common.utils.ListUtil;
import org.dcais.stock.stock.common.utils.StringUtil;
import org.dcais.stock.stock.common.xdevapi.SessionHolder;
import org.dcais.stock.stock.common.xdevapi.XDriveSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public abstract class XCommon {

  protected abstract String getCollName();

  @PostConstruct
  public void postContruct(){
    this.getCollection();
  }

  @Autowired
  private Client xDevApiClient;

  public CollectionIndexInterface getCollectionIndexInterface() {
    return null;
  }

  protected Session getSession(){
    SessionHolder sessionHolder = XDriveSessionManager.getSessionHolder(xDevApiClient);
    return sessionHolder.getSession();
  }

  protected Collection getCollection() {
    Session session = this.getSession();
    Schema schema = session.getDefaultSchema();
    Collection collection = schema.getCollection(getCollName());
    if (DatabaseObject.DbObjectStatus.NOT_EXISTS.equals(collection.existsInDatabase())) {
      collection = schema.createCollection(getCollName());
      CollectionIndexInterface collectionIndexInterface = this.getCollectionIndexInterface();
      if (
        collectionIndexInterface != null
        && ListUtil.isNotBlank(collectionIndexInterface.indexInfo())){
        Collection finalCollection = collection;
        collectionIndexInterface.indexInfo().forEach(collectionIndexInfo -> {
          Result r =this.createIndex(finalCollection,collectionIndexInfo);
       });
      }
    }
    return collection;
  }

  protected Result createIndex(Collection collection , CollectionIndexInfo collectionIndexInfo) {
    Map<String,Object> map = new HashMap<>();
    map.put("fields", collectionIndexInfo.getFields());
    if(StringUtil.isNotBlank(collectionIndexInfo.getType())){
      map.put("type", collectionIndexInfo.getType());
    }
    return collection.createIndex(collectionIndexInfo.getIndexName(), JsonUtil.toJson(map));
  }
}
