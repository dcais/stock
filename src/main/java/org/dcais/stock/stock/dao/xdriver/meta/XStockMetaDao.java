package org.dcais.stock.stock.dao.xdriver.meta;

import com.mysql.cj.xdevapi.Collection;
import com.mysql.cj.xdevapi.DbDoc;
import com.mysql.cj.xdevapi.DbDocImpl;
import com.mysql.cj.xdevapi.JsonString;
import lombok.Getter;
import org.dcais.stock.stock.dao.xdriver.common.XCommonCollDao;
import org.springframework.stereotype.Component;

@Component
public class XStockMetaDao extends XCommonCollDao {
  @Getter
  private String collName = "stock_x_stock_meta";


  public void setStockMeta(String tsCode ,String key ,String value){

    Collection col  = getCollection();
    try{
      DbDoc dbDoc = col.find("_id = :id").bind("id", tsCode).execute().fetchOne();
      if(dbDoc == null ){
        DbDoc tmp = new  DbDocImpl();
        tmp.add(key, new JsonString().setValue(value));
        tmp.add("_id",new JsonString().setValue(value));
        col.add(tmp).execute();
      } else {
        dbDoc.add(key, new JsonString().setValue(value));
        col.modify("_id = :id").set(key,value).bind("id",tsCode).execute();
      }
    } finally {
      col.getSession().close();
    }

  }
}
