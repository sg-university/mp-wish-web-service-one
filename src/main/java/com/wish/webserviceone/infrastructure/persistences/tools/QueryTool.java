package com.wish.webserviceone.infrastructure.persistences.tools;

import org.springframework.stereotype.Component;

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
}
