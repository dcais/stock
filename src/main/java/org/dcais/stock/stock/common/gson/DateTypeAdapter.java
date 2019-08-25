package org.dcais.stock.stock.common.gson;

import com.google.gson.*;
import org.dcais.stock.stock.common.utils.DateUtils;

import java.lang.reflect.Type;
import java.util.Date;

import static org.dcais.stock.stock.common.utils.DateUtils.ISO_DATE_TIME;

/**
 * Created by xujinduo on 6/4/16.
 */
public class DateTypeAdapter implements JsonDeserializer<Date>, JsonSerializer<Date> {

  public Date deserialize(JsonElement json, Type t, JsonDeserializationContext jsc) throws JsonParseException {
    if (!(json instanceof JsonPrimitive)) {
      throw new JsonParseException("The date should be a string value");
    }

    try {
      return DateUtils.smartFormat(json.getAsString());
    } catch (Exception e) {
      throw new JsonParseException(e);
    }
  }

  @Override
  public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
    return src == null ? null : new JsonPrimitive(DateUtils.formatDate(src, DateUtils.ISO_DATE_TIME));
  }
}
