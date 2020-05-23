package org.dcais.stock.stock.common.base.convert;

import org.dcais.stock.stock.common.utils.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

public class BasicConvert extends AbstractConvert {

  @Override
  public Object convert(Object returnValue, Class<?> targetFieldClass) {
    if (returnValue == null || targetFieldClass == null) {
      return null;
    }
    if (targetFieldClass.isAssignableFrom(returnValue.getClass())) {
      return returnValue;
    }
    String returnValueStr = getReturnValueStr(returnValue);
    if (isAssignableFrom(targetFieldClass, Date.class)) {
      return DateUtils.smartFormat(returnValueStr);
    }
    if (isAssignableFrom(targetFieldClass, LocalDateTime.class)){
      // todo:: localDateTime smart format
      return LocalDateUtils.asLocalDateTime(DateUtils.smartFormat(returnValueStr));
    }
    return castNumStr(returnValueStr, targetFieldClass);
  }

  /**
   * 获取returnValue的String
   *
   * @param returnValue
   * @return
   */
  private String getReturnValueStr(Object returnValue) {
    if (returnValue instanceof Boolean) {
      return ConvertUtil.convertBooleanToString((Boolean) returnValue);
    } else if (returnValue instanceof BigDecimal) {
      return MathUtil.toPriceFormat((BigDecimal) returnValue);
    } else {
      return String.valueOf(returnValue).trim();
    }
  }

  private static Object castNumStr(String str, Class<?> targetFieldClass) {
    if (isAssignableFrom(targetFieldClass, Long.class, long.class)) {
      return Long.parseLong(getPointLeft(str));
    } else if (isAssignableFrom(targetFieldClass, Integer.class, int.class)) {
      return Integer.parseInt(getPointLeft(str));
    } else if (isAssignableFrom(targetFieldClass, Short.class, short.class)) {
      return Short.parseShort(getPointLeft(str));
    } else if (isAssignableFrom(targetFieldClass, Double.class, double.class)) {
      return Double.parseDouble(str);
    } else if (isAssignableFrom(targetFieldClass, Float.class, float.class)) {
      return Float.parseFloat(str);
    } else if (isAssignableFrom(targetFieldClass, Boolean.class, boolean.class)) {
      return ConvertUtil.convertStringToBoolean(str);
    } else if (BigDecimal.class.isAssignableFrom(targetFieldClass)) {
      return new BigDecimal(str);
    } else if (String.class.isAssignableFrom(targetFieldClass)) {
      return str;
    } else {
      throw new RuntimeException("未匹配类型.  " + targetFieldClass);
    }
  }

  private static Object castDateStr(String str, Class<?> targetFieldClass) {
    if (isAssignableFrom(targetFieldClass, Date.class)) {
      return DateUtils.smartFormat(str);
    } else {
      throw new RuntimeException("未匹配类型.  " + targetFieldClass);
    }
  }

  private static boolean isAssignableFrom(Class<?> targetFieldClass, Class<?>... clazzs) {
    for (Class<?> clazz : clazzs) {
      if (clazz.isAssignableFrom(targetFieldClass)) {
        return true;
      }
    }
    return false;
  }

  /**
   * 截取小数点左边的
   *
   * @param str
   * @return
   */
  private static String getPointLeft(String str) {
    if (StringUtil.isBlank(str)) {
      return str;
    }
    int index = str.indexOf(".");
    if (index == -1 || index == 0) {
      return str;
    }
    return str.substring(0, index);
  }

}