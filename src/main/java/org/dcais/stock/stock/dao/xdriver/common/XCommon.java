package org.dcais.stock.stock.dao.xdriver.common;

import com.mysql.cj.xdevapi.*;
import org.dcais.stock.stock.common.xdevapi.SessionHolder;
import org.dcais.stock.stock.common.xdevapi.XDriveSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class XCommon {

  protected abstract String getCollName();
  @Autowired
  private Client xDevApiClient;

  protected Session getSession(){
    SessionHolder sessionHolder = XDriveSessionManager.getSessionHolder(xDevApiClient);
    return sessionHolder.getSession();
  }

  protected Collection getCollection() {
    Session session = this.getSession();
    Schema schema = session.getDefaultSchema();
    Collection collection = schema.getCollection(getCollName());
    if (DatabaseObject.DbObjectStatus.NOT_EXISTS.equals(collection.existsInDatabase())) {
      schema.createCollection(getCollName());
    }
    return collection;
  }

}
