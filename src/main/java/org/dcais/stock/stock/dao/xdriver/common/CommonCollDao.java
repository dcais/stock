package org.dcais.stock.stock.dao.xdriver.common;

import com.mysql.cj.xdevapi.*;
import org.springframework.stereotype.Component;

@Component
public abstract class CommonCollDao {

  protected abstract String getCollName();

  protected Collection getCollection(Session session) {
    Schema schema = session.getDefaultSchema();
    Collection collection = schema.getCollection(getCollName());
    if (DatabaseObject.DbObjectStatus.NOT_EXISTS.equals(collection.existsInDatabase())) {
      schema.createCollection(getCollName());
    }
    return collection;
  }

}
