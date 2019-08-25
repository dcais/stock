package org.dcais.stock.stock.dao.xdriver.common;

import com.mysql.cj.xdevapi.*;
import org.dcais.stock.stock.common.xdevapi.XDriverSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class CommonCollDao {
  @Autowired
  private XDriverSession xDriverSession;

  protected abstract String getCollName();

  protected Collection getCollection () {
    Session session =xDriverSession.getSesssion();
    Schema schema = session.getDefaultSchema();
    Collection collection = schema.getCollection(getCollName());
    if(DatabaseObject.DbObjectStatus.NOT_EXISTS.equals(collection.existsInDatabase())){
      schema.createCollection(getCollName());
    }
    return collection;
  }


}
