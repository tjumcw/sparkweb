package com.mcw.sparkweb.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ReflectUtils {

    /**
     * 将 Java 实体类对象转换为 Map
     *
     * @param entity Java 实体类对象
     * @return 转换后的 Map
     */
    public static Map<String, Object> convertEntityToMap(Object entity) {
        Map<String, Object> map = new HashMap<>();

        // 获取类的所有字段
        Field[] fields = entity.getClass().getDeclaredFields();

        for (Field field : fields) {
            try {
                // 构造 getter 方法名
                String getterMethodName = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);

                // 获取 getter 方法
                Method getterMethod = entity.getClass().getMethod(getterMethodName);

                // 调用 getter 方法获取字段值
                Object value = getterMethod.invoke(entity);

                // 将字段名和字段值放入 Map
                map.put(field.getName(), value);
            } catch (Exception e) {
                e.printStackTrace(); // 处理异常，可以根据实际情况进行调整
            }
        }

        return map;
    }
}
