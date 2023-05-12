package com.cjf.demo.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @author : chenjianfeng
 * @date : 2023/5/8
 */
public class JsonUtil {
    public static final ObjectMapper CAMEL_CASE_OBJECT_MAPPER = new ObjectMapper();

    public JsonUtil() {
    }

    public static <T> T jsonToObject(String json, Class<T> type) {
        if (null == json) {
            return null;
        } else {
            try {
                return CAMEL_CASE_OBJECT_MAPPER.readValue(json, type);
            } catch (IOException var3) {
                throw new RuntimeException(var3);
            }
        }
    }

    public static <T> T jsonToObject(String json, TypeReference<T> typeReference) {
        if (null == json) {
            return null;
        } else {
            try {
                return CAMEL_CASE_OBJECT_MAPPER.readValue(json, typeReference);
            } catch (IOException var3) {
                throw new RuntimeException(var3);
            }
        }
    }

    public static <T> String toJsonString(T object) {
        if (null == object) {
            return null;
        } else {
            try {
                return CAMEL_CASE_OBJECT_MAPPER.writeValueAsString(object);
            } catch (IOException var2) {
                throw new RuntimeException(var2);
            }
        }
    }

    static {
        CAMEL_CASE_OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        CAMEL_CASE_OBJECT_MAPPER.findAndRegisterModules();
    }
}
