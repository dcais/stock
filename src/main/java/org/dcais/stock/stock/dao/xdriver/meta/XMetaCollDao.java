package org.dcais.stock.stock.dao.xdriver.meta;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mysql.cj.xdevapi.*;
import lombok.Getter;
import org.dcais.stock.stock.common.utils.JsonUtil;
import org.dcais.stock.stock.dao.xdriver.common.XCommonCollDao;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class XMetaCollDao extends XCommonCollDao {

  @Getter
  private String collName = "stock_x_meta";


  public void put(String key, Object value) {
    Map<String, Object> map = new HashMap<>();
    map.put("value", value);
    String jsonString = JsonUtil.toJson(map);
    Collection collection = getCollection();
    collection.addOrReplaceOne(key, jsonString);
    collection.getSession().close();
  }

  public Object get(String key) {
    Collection collection = getCollection();
    DbDoc dbDoc = collection.getOne(key);
    collection.getSession().close();
    if (dbDoc == null) {
      return null;
    }
    Gson gson = JsonUtil.getGsonObj();
    Map<String, Object> map = gson.fromJson(dbDoc.toString(), new TypeToken<Map<String, Object>>() {
    }.getType());
    return map.get("value");
  }

}
