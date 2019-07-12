package org.dcais.stock.stock.biz.tushare.parser;

import lombok.Data;
import org.dcais.stock.stock.annotation.TuShareField;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

@Data
public class ParseFieldInfo {
    private Field targetField;
    private Method targetSetMethod;

    private TuShareField tuShareField;

}
