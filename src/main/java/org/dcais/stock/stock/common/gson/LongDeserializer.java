package org.dcais.stock.stock.common.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class LongDeserializer implements JsonDeserializer<Long> {

  @Override
  public Long deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
    try {
      return element.getAsLong();
    } catch (NumberFormatException e) {
      return null;
    }
  }

}