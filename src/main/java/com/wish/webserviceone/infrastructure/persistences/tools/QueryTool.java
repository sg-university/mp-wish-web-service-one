package com.wish.webserviceone.infrastructure.persistences.tools;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Component
public class QueryTool {

    public QueryTool() {
    }

    public String addFilterForUnNamedParameters(String query, Map<String, String> filter) {
        StringBuilder newQuery = new StringBuilder(query);
        if (!filter.isEmpty()) {
            newQuery.append(" where ");
            Iterator<Map.Entry<String, String>> it = filter.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, String> entry = it.next();
                newQuery.append(String.format("%s = ? ", entry.getKey()));
                if (filter.size() > 1 && it.hasNext()) {
                    newQuery.append("and ");
                }
            }
        }
        return newQuery.toString().trim();
    }

    public <T> Map<String, Object> convertFilterForUnNamedParameters(Map<String, String> filter, Class<T> classReference) throws JsonProcessingException, IllegalAccessException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonResult = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(filter);
        T objectResult = mapper.readValue(jsonResult, classReference);
        TypeReference<Map<String, Object>> typeRefMap = new TypeReference<Map<String, Object>>() {
        };
        Map<String, Object> mapResult = new HashMap<String, Object>();
        for (Field field : objectResult.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            JsonProperty column = field.getAnnotation(JsonProperty.class);
            String name = column == null ? field.getName() : column.value();
            Object value = field.get(objectResult);
            if (value == null) continue;
            mapResult.put(name, value);
        }

        return mapResult;
    }

}
