package org.dcais.stock.stock.common.utils;

import com.sun.tools.javac.util.StringUtils;

import java.lang.reflect.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReflectUtils {
    public static final String  DATASTYLE = "yyyy-MM-dd HH:mm:ss";

    private static final String GET_PRE   = "get";

    /**
     * bean中类型可以是string, date, 基本类型和 EnumInterface<String>
     *
     * @param bean
     * @return
     */
    public static Map<String, String> beanToMap(Object bean) {
        Map<String, String> retMap = new HashMap<String, String>();
        if (bean == null) {
            return retMap;
        }
        for (Method method : bean.getClass().getMethods()) {
            if (method.getName().startsWith(GET_PRE)) {
                String fieldNameStart = StringUtils.toLowerCase(StringUtil.substring(
                        method.getName(), 3, 4));
                if (StringUtil.isBlank(fieldNameStart)) {
                    continue;
                }
                String fieldName = fieldNameStart + StringUtil.substring(method.getName(), 4);
                Object returnValue;
                try {
                    returnValue = method.invoke(bean);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                if (returnValue == null) {
                    continue;
                }
                if (returnValue instanceof Date) {
                    retMap.put(fieldName,
                            new SimpleDateFormat(DATASTYLE).format((Date) returnValue));
                }

                else {
                    retMap.put(fieldName, String.valueOf(returnValue));
                }
            }
        }
        return retMap;
    }

    /**
     * @param methodStr
     * @param obj
     * @param param
     * @return
     */
    public static Object invokeMethod(String methodStr, Object obj, Object... param) {
        try {
            Class<?>[] classes = getObjectClasses(param);
            Method method = null;
            if (classes != null) {
                method = getMethod(methodStr, obj, classes);
            } else {
                method = getMethod(methodStr, obj);
            }
            if (method == null) {
                throw new RuntimeException("method: " + methodStr + " not found.   obj:" + obj);
            }
            return method.invoke(obj, param);
        } catch (Exception e) {
            throw new RuntimeException("method: " + methodStr + ". obj:" + obj, e);
        }
    }

    /**
     * 获取param的类型
     *
     * @param param
     * @return
     */
    public static Class<?>[] getObjectClasses(Object... param) {
        if (param == null) {
            return null;
        }
        Class<?>[] classes = new Class<?>[param.length];
        for (int i = 0; i < param.length; i++) {
            classes[i] = param[i].getClass();
        }
        return classes;
    }

    /**
     * 调用obj的method方法. 入参是param
     *
     * @param method
     * @param obj
     * @param param
     * @return
     */
    public static Object invokeMethod(Method method, Object obj, Object... param) {
        try {
            return method.invoke(obj, param);
        } catch (Exception e) {
            throw new RuntimeException("method: " + method + ". obj Class:" + obj.getClass() + ". ", e);
        }
    }

    /**
     * 首字母大写, 前面加is 如果fieldStr是以is开头, 且后面首字母大写, 直接返回 fieldStr
     * ReflectUtil.getIsBooleanMethod("is") = "isIs";
     * ReflectUtil.getIsBooleanMethod("abc") = "isAbc";
     * ReflectUtil.getIsBooleanMethod("isa") = "isIsa";
     * ReflectUtil.getIsBooleanMethod("i") = "isI";
     * ReflectUtil.getIsBooleanMethod("isAbc") = "isAbc";
     * ReflectUtil.getIsBooleanMethod("isabc") = "isIsabc";
     *
     * @param fieldStr
     * @return
     */
    public static String getIsBooleanMethod(String fieldStr) {
        return "is" + toUpperCaseFirstChar(getFilterIsStr(fieldStr));
    }

    /**
     * 首字母大写, 前面加set 如果fieldStr是以is开头, 且后面首字母大写, 将is换成set返回.
     * ReflectUtil.getSetBooleanMethod("is") = "setIs";
     * ReflectUtil.getSetBooleanMethod("abc") = "setAbc";
     * ReflectUtil.getSetBooleanMethod("isa") = "setA";
     * ReflectUtil.getSetBooleanMethod("i") = "setI";
     * ReflectUtil.getSetBooleanMethod("isAbc")= "setAbc";
     * ReflectUtil.getSetBooleanMethod("istest")= "setIstest";
     *
     * @param fieldStr
     * @return
     */
    public static String getSetBooleanMethod(String fieldStr) {
        return "set" + toUpperCaseFirstChar(getFilterIsStr(fieldStr));
    }

    /**
     * 将第一个字母大写
     *
     * @param str
     * @return
     */
    public static String toUpperCaseFirstChar(String str) {
        if (StringUtil.isBlank(str)) {
            return StringUtil.EMPTY_STRING;
        }
        String firstChar = StringUtil.substring(str, 0, 1);
        return firstChar.toUpperCase() + StringUtil.substring(str, 1, str.length());
    }

    /**
     * 如果以is开头, 且is后面的首字母大写, 去除is返回 否则直接返回
     *
     * @param str
     * @return
     */
    private static String getFilterIsStr(String str) {
        if (StringUtil.isBlank(str)) {
            throw new RuntimeException("参数为空");
        }
        String isFlag = "is";
        if (str.startsWith(isFlag)) {
            String sub = StringUtil.substring(str, isFlag.length(), isFlag.length() + 1);
            if (StringUtil.isNotBlank(sub) && StringUtil.equals(sub, StringUtil.toUpperCase(sub))) {
                return StringUtil.substring(str, isFlag.length(), str.length());
            }
        }
        return str;
    }

    /**
     * 返回get方法
     *
     * @param field
     * @param obj
     * @return
     */
    public static Method getGetMethod(String fieldStr, Object obj) {
        Field field = getField(fieldStr, obj);
        String methodStr;
        if ((field != null) && (boolean.class.isAssignableFrom(field.getType()))) {
            methodStr = getIsBooleanMethod(fieldStr);
        } else {
            methodStr = "get" + toUpperCaseFirstChar(fieldStr);
        }
        try {
            return obj.getClass().getMethod(methodStr);
        } catch (NoSuchMethodException e) {
            //
        } catch (SecurityException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * 返回set方法
     *
     * @param field
     * @param obj
     * @return
     */
    public static Method getSetMethod(String fieldStr, Object obj, Class<?> paramClass) {
        Field field = getField(fieldStr, obj);
        return getSetMethod(field, obj, paramClass);
   }

    public static Method getSetMethod(Field field, Object obj){
        return getSetMethod(field,obj, field.getType());
    }
    public static Method getSetMethod(Field field, Object obj, Class<?> paramClass){
        String methodStr;
        String fieldStr = field.getName();
        if(field == null){
            return null;
        }

        if ((boolean.class.isAssignableFrom(field.getType()))) {
            methodStr = getSetBooleanMethod(fieldStr);
        } else {
            methodStr = "set" + toUpperCaseFirstChar(fieldStr);
        }

        try {
            return obj.getClass().getMethod(methodStr, paramClass);
        } catch (SecurityException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            // 没有找到方法。直接返回null
        }
        return null;

    }

    /**
     * 返回Method
     *
     * @param method
     * @param obj
     * @param clazz
     * @return
     */
    public static Method getMethod(String method, Object obj, Class<?>... clazz) {
        try {
            return obj.getClass().getMethod(method, clazz);
        } catch (NoSuchMethodException e) {

        } catch (SecurityException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * 获取Field
     *
     * @param field
     * @param obj
     * @return
     */
    public static Field getField(String fieldStr, Object obj) {

        Field field = getField(fieldStr, obj.getClass());
        if (field != null) {
            return field;
        }
        Class<?> currentSupperClass = obj.getClass().getSuperclass();
        while (!currentSupperClass.equals(Object.class)) {
            field = getField(fieldStr, currentSupperClass);
            if (field != null) {
                return field;
            }
            currentSupperClass = currentSupperClass.getSuperclass();
        }
        return null;
    }

    public static List<Field> getFieldList(Class<?> clazz){
        List<Field> list = new ArrayList<Field>();
        Class<?> currentSupperClass = clazz.getSuperclass();
        while (!Object.class.equals(currentSupperClass)) {
            list.addAll(Arrays.asList(currentSupperClass.getDeclaredFields()));
            currentSupperClass = currentSupperClass.getSuperclass();
        }
        list.addAll(Arrays.asList(clazz.getDeclaredFields()));
        return list;
    }



    /**
     * @param clazzStr
     * @return
     */
    public static Object newInstance(String clazzStr) {
        if (clazzStr == null) {
            return null;
        }
        try {
            return Class.forName(clazzStr).newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 实例化clazz
     *
     * @param clazz
     * @return
     */
    public static <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(clazz + " 实例化失败", e);
        }
    }

    /**
     * 获取Class类
     *
     * @param className
     * @return
     */
    public static Class<?> getClass(String className) {
        try {
            return Class.forName(className);
        } catch (Exception e) {
            throw new RuntimeException("not found class by name:" + className, e);
        }
    }

    /**
     * 判断targetFieldClass是否是clazzs的类型等
     *
     * @param targetFieldClass
     * @param clazzs
     * @return
     */
    public static boolean isAssignableFrom(Class<?> targetFieldClass, Class<?>... clazzs) {
        for (Class<?> clazz : clazzs) {
            if (clazz.isAssignableFrom(targetFieldClass)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 返回field中内部类型 如果field是 String reutrn null 如果field是 Collection return null
     * 如果field是 Collection<String> return java.lang.String
     *
     * @param field
     * @return
     */
    protected static String getCollectionContentType(Type type) {
        String str = type.toString();
        int start = str.indexOf("<");
        if (start == -1 || start == (str.length() - 1))
            return null;
        int end = str.indexOf(">");
        if (end == -1)
            return null;
        if (end < start)
            return null;
        return str.substring(start + 1, end);
    }

    private static Field getField(String fieldStr, Class<?> clazz) {
        try {
            return clazz.getDeclaredField(fieldStr);
        } catch (NoSuchFieldException e) {
            //
        } catch (SecurityException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * 获取clazz的接口泛型和clazz父类的接口泛型, 以后再优化
     *
     * @param clazz
     * @return
     */
    public static Set<Type> getGenericTypes(Class<?> clazz) {
        Set<Type> set = new HashSet<Type>();
        if (clazz == null) {
            return set;
        }
        set.addAll(Arrays.asList(clazz.getGenericInterfaces()));
        Type superClassType = clazz.getGenericSuperclass();
        if (superClassType != null) {
            set.add(superClassType);
        }
        set.addAll(getGenericTypes(clazz.getSuperclass()));
        for (Class<?> superInterface : clazz.getInterfaces()) {
            set.addAll(getGenericTypes(superInterface));
        }
        return set;
    }

    /**
     * 根据typeList 获取 继承自superClazz的 第一个泛型
     *
     * @param typeList
     * @param superClazz
     * @return
     */
    public static Class<?> getGenericType(Set<Type> typeList, Class<?> superClazz) {
        List<Class<?>> list = new ArrayList<Class<?>>();
        list.addAll(getGenericTypeSet(typeList, superClazz));
        if (list == null || list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    private static Set<Class<?>> getGenericTypeSet(Set<Type> typeSet, Class<?> superClazz) {
        return getGenericTypeSet(typeSet, superClazz, null);

    }

    /**
     * 根据typeList 获取 继承自superClazz的所有 泛型
     *
     * @param typeList
     * @param superClazz
     * @return
     */
    private static Set<Class<?>> getGenericTypeSet(Set<Type> typeSet, Class<?> superClazz,
                                                   Class<?> filterClazz) {
        Set<Class<?>> set = new HashSet<Class<?>>();
        if (typeSet == null || typeSet.size() == 0) { // 如果没有接口，找父类
            return set;
        }
        for (Type type : typeSet) {// 遍历接口或父类 类型
            if (!ParameterizedType.class.isAssignableFrom(type.getClass())) {
                continue;
            }
            ParameterizedType paramType = (ParameterizedType) type;
            if (!(paramType.getRawType() instanceof Class)) {
                continue;
            }
            Class<?> typeClass = (Class<?>) paramType.getRawType();
            if (!typeClass.isAssignableFrom(superClazz) || typeClass.equals(filterClazz)) { // 找到指定的接口或父类类型
                continue;
            }
            Type[] contentTypes = paramType.getActualTypeArguments();
            if (contentTypes == null || contentTypes.length == 0) {
                continue;
            }
            for (Type contentType : contentTypes) {
                if (ParameterizedType.class.isAssignableFrom(contentType.getClass())) { // 如果是多级泛型
                    ParameterizedType parameterizedType = (ParameterizedType) contentType;
                    set.add((Class<?>) parameterizedType.getRawType());
                } else if (Class.class.isAssignableFrom(contentType.getClass())) {
                    set.add((Class<?>) contentType);
                } else if (TypeVariable.class.isAssignableFrom(contentType.getClass())) {
                    @SuppressWarnings("unchecked")
                    TypeVariable<Class<?>> typeVariable = ((TypeVariable<Class<?>>) contentType);
                    Class<?> tClass = typeVariable.getGenericDeclaration();
                    set.addAll(getGenericTypeSet(typeSet, tClass, superClazz));
                }

            }
        }
        return set;

    }

    public static List<Class<?>> getGenericTypeList(Class<?> clazz, Class<?> superClazz) {
        Set<Type> typeSet = getGenericTypes(clazz);
        List<Class<?>> list = new ArrayList<Class<?>>();
        list.addAll(getGenericTypeSet(typeSet, superClazz));
        return list;
    }

    /**
     * 返回clazz定义中继承自superClazz的内部类型,先匹配接口，匹配不到在匹配父类
     *
     * @param clazz 当前类
     * @param superClazz 父类或接口
     * @return 第一个内部类型
     */
    public static Class<?> getGenericType(Class<?> clazz, Class<?> superClazz) {
        List<Class<?>> list = getGenericTypeList(clazz, superClazz);
        if (list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    /**
     * 返回clazz定义中继承自superClazz的内部类型,先匹配接口，匹配不到在匹配父类
     *
     * @param clazz 当前类
     * @param superClazz 父类或接口
     * @return 第一个内部类型
     */
    public static Class<?> getGenericType(Class<?> clazz, Class<?> superClazz, int index) {
        List<Class<?>> list = getGenericTypeList(clazz, superClazz);
        if (list.size() == 0) {
            return null;
        }
        if (list.size() <= index) {
            return null;
        }
        return list.get(index);
    }

    /**
     * 获取指定annotation方法,方法必须有返回值
     *
     * @param Clazz
     * @param annotationClass
     * @param returnType
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static List<Method> getAnnotationMethods(Class<?> Clazz, Class annotationClass) {
        List<Method> list = new ArrayList<Method>();
        for (Method method : Clazz.getMethods()) {
            if (method.getAnnotation(annotationClass) != null && method.getReturnType() != null) {
                list.add(method);
            }
        }
        return list;
    }


    /**
     * 获取第一层范型类型
     * @param field
     * @return
     */
    public static Class<?> getGenericType(Field field){
        if(field==null){
            return null;
        }
        Type type = field.getGenericType();
        if(type==null){
            return field.getType();
        }
        return getGenericType(type);
    }


    public static Class<?> getGenericType(Type type){
        if(type==null){
            return null;
        }
        if (type instanceof ParameterizedType) {
            Type[] aTypes = ((ParameterizedType)type).getActualTypeArguments();
            if(aTypes==null||aTypes.length==0){
                return null;
            }
            return (Class<?>)aTypes[0];
        }
        return null;
    }
}
