package org.dcais.stock.stock.biz.tushare.parser;

import com.google.common.base.CaseFormat;
import lombok.extern.slf4j.Slf4j;
import org.dcais.stock.stock.annotation.TuShareField;
import org.dcais.stock.stock.common.base.convert.BasicConvert;
import org.dcais.stock.stock.common.utils.ConvertUtil;
import org.dcais.stock.stock.common.utils.ReflectUtils;
import org.dcais.stock.stock.http.tushare.result.TushareData;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Slf4j
public class TushareDataParser {

  public static <T extends Object> List<T> parse(TushareData data, Class<T> targetClazz) {
    List<String> turFieldNames = data.getFields();

    List<Field> targetFields = ReflectUtils.getFieldList(targetClazz);
    List<T> rets = new ArrayList<>();
    for (List<Object> item : data.getItems()) {
      try {
        T instance = targetClazz.newInstance();
        Iterator itr = item.iterator();
        int idx = 0;
        while (itr.hasNext()) {
          Object value = itr.next();
          String srcFieldName = turFieldNames.get(idx);
          idx++;

          Field field = findField(srcFieldName, targetFields);
          if (field == null) {
            continue;
          }
          Method setMethod = ReflectUtils.getSetMethod(field, instance);
          if (setMethod == null) {
            continue;
          }
          Object targetValue = ConvertUtil.invokeConvert(value, field.getType(), BasicConvert.class);
          try {
            setMethod.invoke(instance, targetValue);
          } catch (InvocationTargetException e) {
            log.error("", e);
          }
        }
        rets.add(instance);
      } catch (InstantiationException | IllegalAccessException e) {
        log.error("", e);
      }

    }
    return rets;
  }

  public static Field findField(String findName, List<Field> fields) {
    Field rField = null;
    for (Field field : fields) {
      TuShareField tuShareField = field.getAnnotation(TuShareField.class);
      if (tuShareField != null) {
        if (findName.equals(tuShareField.value())) {
          rField = field;
          break;
        }
      }
      String fieldName = field.getName();
      if (findName.equals(fieldName)) {
        rField = field;
        break;
      }

      if (findName.indexOf("_") > -1) {
        String lu = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, fieldName);
        if (findName.equals(lu)) {
          rField = field;
          break;
        }
      }
    }
    return rField;
  }
}
