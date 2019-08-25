package org.dcais.stock.stock.common.utils;

import org.dcais.stock.stock.common.base.convert.AbstractConvert;
import org.dcais.stock.stock.common.base.convert.ConvertExtendInterface;

import java.util.HashMap;
import java.util.Map;

public class ConvertUtil {
  private static Map<Class<?>, ConvertExtendInterface> convertInstanceMap = new HashMap<Class<?>, ConvertExtendInterface>();

  /**
   * 将value转换为Y或N
   *
   * @param value
   * @return
   */
  public static String convertBooleanToString(Boolean value) {
    if (value == null) {
      throw new IllegalArgumentException("value is null");
    }
    if (value) {
      return "Y";
    } else {
      return "N";
    }
  }

  public static boolean convertStringToBoolean(String value) {
    if (StringUtil.equals("1", StringUtil.trim(value))
      || StringUtil.equals("Y", StringUtil.trim(value))) {
      return true;
    } else if (StringUtil.equals("0", StringUtil.trim(value))
      || StringUtil.equals("N", StringUtil.trim(value))) {
      return false;
    }
    throw new IllegalArgumentException("not exists define " + value + ". only deine: 1,0,Y,N");
  }

  /**
   * 调用convertUtil特性转换。
   *
   * @param value            原值
   * @param targetFieldClass 目标targetFieldClass描述
   * @param clazz            AbstractConvert插件类
   * @return 转换后的值
   */
  public static Object invokeConvert(Object value, Class<?> targetFieldClass,
                                     Class<? extends AbstractConvert> clazz) {
    if (clazz == null || targetFieldClass == null) {
      return value;
    }
    AbstractConvert convertImpl = (AbstractConvert) getConvertInstance(clazz);
    return convertImpl.convert(value, targetFieldClass);

  }


  private static ConvertExtendInterface getConvertInstance(Class<? extends ConvertExtendInterface> clazz) {
    try {
      ConvertExtendInterface ci = convertInstanceMap.get(clazz);
      if (ci == null) {
        ci = clazz.newInstance();
        convertInstanceMap.put(clazz, ci);
      }
      return ci;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
