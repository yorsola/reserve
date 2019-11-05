package com.ac.reserve.common.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.text.SimpleDateFormat;

public class JsonUtil {
    private JsonUtil() {
    }

    public static String toJSONString(Object object) {
        return generateJSONString(object, false, false, false);
    }

    public static String toIgnoreEmptyOrderedJSONStringNoIndent(Object object) {
        return generateJSONString(object, true, true, true);
    }

    public static String generateJSONString(Object object, boolean needOrder, boolean ignoreEmpty,
                                            boolean ignoreIndent) {
        String result = null;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, needOrder);
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, ignoreIndent);
        if (ignoreEmpty) {
            objectMapper.setSerializationInclusion(Include.NON_EMPTY);
        }

        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        try {
            result = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
//            e.printStackTrace();
        }

        return result;
    }

    public static String makeJSONMap(Object object, String dfm) {
        String result = null;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_MISSING_VALUES, true);
        objectMapper.setDateFormat(new SimpleDateFormat(dfm));

        try {
            result = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
//            e.printStackTrace();
        }

        return result;
    }
}
