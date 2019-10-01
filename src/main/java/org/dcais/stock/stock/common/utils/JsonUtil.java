package org.dcais.stock.stock.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.dcais.stock.stock.common.gson.*;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static java.lang.reflect.Modifier.STATIC;
import static java.lang.reflect.Modifier.TRANSIENT;

/**
 * Created by Administrator on 14-9-12.
 */
public final class JsonUtil {

  private static ObjectMapper mapper = new ObjectMapper();

  private JsonUtil() {
  }

  public static Gson getGsonObj() {
    return getCommonGsonBuilder().create();
  }

  public static Gson getNameWithUnderscoresGsonObj() {
    return getCommonGsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
  }

  public static GsonBuilder getCommonGsonBuilder() {
    return new GsonBuilder()
      .excludeFieldsWithModifiers(STATIC)
      .excludeFieldsWithModifiers(TRANSIENT)
      .serializeNulls()
      .registerTypeAdapter(Date.class, new DateTypeAdapter())
      .registerTypeAdapter(Double.class, new DoubleDeserializer())
      .registerTypeAdapter(BigDecimal.class, new BigDecimalDeserializer())
      .registerTypeAdapter(Long.class, new LongDeserializer())
      .registerTypeAdapter(Integer.class, new IntegerDeserializer());
  }

  public static <T> List<T> fromJsonList(String json, Class<T> clazz) {
    Gson gson = getGsonObj();
    return gson.fromJson(json, new TypeToken<List<T>>() {
    }.getType());
  }

  /**
   * 对象转换成json字符串
   *
   * @param obj
   * @return
   */
  public static String toJson(Object obj) {
    Gson gson = getGsonObj();
    return gson.toJson(obj);
  }

  /**
   * json字符串转成对象
   *
   * @param str
   * @param type
   * @return
   */
  public static <T> T fromJson(String str, Type type) {
    Gson gson = getGsonObj();
    return gson.fromJson(str, type);
  }

  /**
   * json字符串转成对象
   *
   * @param str
   * @param type
   * @return
   */
  public static <T> T fromJson(String str, Class<T> type) {
    Gson gson = getGsonObj();
    return gson.fromJson(str, type);
  }

  public static ObjectMapper getMapper() {
    return mapper;
  }


  public static <T> List<T> stringToList(String jsonString, Class<T[]> type) {

    Gson gson = new Gson();
    T[] list = gson.fromJson(jsonString, type);

    return Arrays.asList(list);

  }

}
