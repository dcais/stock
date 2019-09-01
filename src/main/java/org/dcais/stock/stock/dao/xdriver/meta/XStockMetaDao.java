package org.dcais.stock.stock.dao.xdriver.meta;

import com.mysql.cj.xdevapi.*;
import lombok.Getter;
import org.dcais.stock.stock.dao.xdriver.common.XCommon;
import org.springframework.stereotype.Component;

@Component
public class XStockMetaDao extends XCommon {
  @Getter
  private String collName = "stock_x_stock_meta";


  public void setStockMeta(String tsCode ,String key ,String value){

    Collection col  = getCollection();
    DbDoc dbDoc = col.find("_id = :id").bind("id", tsCode).execute().fetchOne();
    if(dbDoc == null ){
      DbDoc tmp = new  DbDocImpl();
      tmp.add("_id",new JsonString().setValue(tsCode));
      tmp.add(key, new JsonString().setValue(value));
      col.add(tmp).execute();
    } else {
      dbDoc.add(key, new JsonString().setValue(value));
      col.modify("_id = :id").set(key,value).bind("id",tsCode).execute();
    }
  }

  public String getStockMeta(String tsCode, String key){
    Collection col  = getCollection();
    DbDoc dbDoc = col.getOne(tsCode);
    JsonString jsonValue = (JsonString) dbDoc.get(key);
    return jsonValue.getString();
  }
}
