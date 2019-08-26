package org.dcais.stock.stock.dao.xdriver.common;

import com.mysql.cj.xdevapi.*;
import org.dcais.stock.stock.common.xdevapi.XDriverSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class XCommonCollDao {

  protected abstract String getCollName();
  @Autowired
  private XDriverSession xDriverSession;

  protected Session getSession(){
    return xDriverSession.getSession();
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
