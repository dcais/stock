package org.dcais.stock.stock.common.base.convert;

public class DefaultConvert extends AbstractConvert {

  public DefaultConvert() {

  }

  @Override
  public Object convert(Object returnValue, Class<?> targetFieldClass) {
    if (targetFieldClass.isAssignableFrom(returnValue.getClass())) {
      return returnValue;
    }
    return targetFieldClass.cast(returnValue);
  }

}