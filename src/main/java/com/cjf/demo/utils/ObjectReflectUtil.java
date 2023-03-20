package com.cjf.demo.utils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;

/**
 * @author : chenjianfeng
 * @date : 2023/3/20
 */
public class ObjectReflectUtil {

    /**
     * 根据属性，拿到set方法，并把值set到对象中
     *
     * @param obj   对象
     * @param value
     */
    public static void setValue(Object obj, String filedName, Object value) throws NoSuchFieldException {
        Class<?> clazz = obj.getClass();
        String methodNameStr = "set" + filedName.substring(0, 1).toUpperCase() + filedName.substring(1);
        Field methodNameField = obj.getClass().getDeclaredField(filedName);
        methodNameField.getType();
        Class<?> typeClass = obj.getClass().getDeclaredField(filedName).getType();
        try {
            Method method = clazz.getDeclaredMethod(methodNameStr, new Class[]{typeClass});
            method.invoke(obj, new Object[]{getClassTypeValue(typeClass, value)});
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    /**
     * 通过class类型获取获取对应类型的值
     *
     * @param typeClass class类型
     * @param value     值
     * @return Object
     */
    private static Object getClassTypeValue(Class<?> typeClass, Object value) {
        if (typeClass == int.class || value instanceof Integer) {
            if (null == value) {
                return 0;
            }
            return value;
        } else if (typeClass == short.class) {
            if (null == value) {
                return 0;
            }
            return value;
        } else if (typeClass == byte.class) {
            if (null == value) {
                return 0;
            }
            return value;
        } else if (typeClass == double.class) {
            if (null == value) {
                return 0;
            }
            return value;
        } else if (typeClass == long.class) {
            if (null == value) {
                return 0;
            }
            return value;
        } else if (typeClass == String.class) {
            if (null == value) {
                return "";
            }
            return value;
        } else if (typeClass == boolean.class) {
            if (null == value) {
                return true;
            }
            return value;
        } else if (typeClass == BigDecimal.class) {
            if (null == value) {
                return new BigDecimal(0);
            }
            return new BigDecimal(value + "");
        } else {
            return typeClass.cast(value);
        }
    }

    public static void main(String[] args) throws NoSuchFieldException {

    }

    public static Object getValue(Object obj,String filedName) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
        Class<?> classZ = obj.getClass();
//        String methodNameStr = "get" + filedName.substring(0, 1).toUpperCase() + filedName.substring(1);

        //获得属性
        Field[] fields = obj.getClass().getDeclaredFields();
        PropertyDescriptor pd = new PropertyDescriptor(filedName, classZ);
        //获得get方法
        Method getMethod = pd.getReadMethod();
        //执行get方法返回一个Object
        return getMethod.invoke(obj);

    }
}
