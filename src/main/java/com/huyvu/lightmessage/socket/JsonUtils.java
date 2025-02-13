package com.huyvu.lightmessage.socket;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import org.json.JSONObject;

public class JsonUtils {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new JavaTimeModule());;

    private static final ObjectMapper OBJECT_MAPPER_NOTNULL = new ObjectMapper().registerModule(new JavaTimeModule());;

    static {
        OBJECT_MAPPER_NOTNULL.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @SneakyThrows
    public static JSONObject toJsonObj(Object obj) {
        String s = OBJECT_MAPPER.writeValueAsString(obj);
        return new JSONObject(s);
    }

    @SneakyThrows
    public static JSONObject toJsonObjExcludeNull(Object obj) {
        String s = OBJECT_MAPPER_NOTNULL.writeValueAsString(obj);
        return new JSONObject(s);
    }

    @SneakyThrows
    public static <T> T toPojoObj(String stringJson, Class<T> clazz) {
        return OBJECT_MAPPER.readValue(stringJson, clazz);
    }

}