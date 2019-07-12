package org.dcais.stock.stock.common.base.convert;

import java.lang.reflect.Field;

public interface ConvertExtendInterface {

    /**
     * @param returnValue get方法返回的对象
     * @param targetField 目标属性的类型
     * @return
     */
    public Object convert(Object returnValue, Field targetField);

}