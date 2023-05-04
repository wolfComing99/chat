//package com.wolf.utils;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.serializer.SerializerFeature;
//
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//
//public class JsonUtil {
//
//    /**
//     * 将对象转为 JSON 字符串
//     *
//     * @param obj 要转换的对象
//     * @return JSON 字符串
//     */
//    public static String toJsonString(Object obj) {
//        // 配置 fastjson 序列化参数
//        SerializerFeature[] features = {
//                // 保留空的字段
//                SerializerFeature.WriteMapNullValue,
//                // 将 LocalDateTime 格式化为 yyyy-MM-dd HH:mm:ss
//                SerializerFeature.WriteDateUseDateFormat
//        };
//        // 定义 LocalDateTime 的序列化格式
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        // 配置 LocalDateTime 的序列化方式
//        JSON.defaultSerializeConfig.put(LocalDateTime.class, (serializer, object, fieldName, fieldType, features1) ->
//                serializer.write(formatter.format((LocalDateTime) object)));
//
//        // 调用 fastjson 序列化方法，将对象转换为 JSON 字符串
//        return JSON.toJSONString(obj, features);
//    }
//}
//
