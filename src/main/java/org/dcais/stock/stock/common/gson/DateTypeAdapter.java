package org.dcais.stock.stock.common.gson;

import com.google.gson.*;
import org.dcais.stock.stock.common.utils.DateUtils;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * Created by xujinduo on 6/4/16.
 */
public class DateTypeAdapter implements JsonDeserializer<Date> {

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
}
