package org.dcais.stock.stock.common.utils;

import com.fasterxml.jackson.databind.util.ClassUtil;

public class CommonUtils {
  public static <T> T getValue(Object value, Class<T> clazz) {
    if(value == null){
      return null;
    }
    if(value.getClass() == clazz){
      return (T) value;
    }
    else{
      String strValue = value.toString();
      if(java.lang.Integer.class == clazz){
        return (T) Integer.valueOf(strValue);
      }
      else if ( java.lang.Long.class == clazz){
        return (T) Long.valueOf(strValue);
      }
      else if (java.lang.String.class == clazz){
        return (T) strValue;
      }
      else if (java.lang.Double.class == clazz){
        return (T) Double.valueOf(strValue);
      }
      else if (java.lang.Float.class == clazz){
        return (T) Float.valueOf(strValue);
      }
      else if (java.util.Date.class == clazz) {
        return (T) DateUtils.smartFormat(strValue);

      }
      else{
        throw new RuntimeException("unsuport class" + clazz);
      }
    }
  }
}
