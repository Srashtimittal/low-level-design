package com.conceptandcoding.LowLevelDesign.LLDCarRentalSystem.api;

import java.util.HashMap;
import java.util.Map;

public class SimpleJson {

    public static Map<String, String> parse(String json) {
        Map<String, String> map = new HashMap<>();

        json = json.trim();

        if (json.startsWith("{")) json = json.substring(1);
        if (json.endsWith("}")) json = json.substring(0, json.length() - 1);

        if (json.isBlank()) return map;

        for (String pair : json.split(",")) {
            String[] keyValue = pair.split(":", 2);
            if (keyValue.length != 2) continue;

            map.put(clean(keyValue[0]), clean(keyValue[1]));
        }

        return map;
    }

    private static String clean(String value) {
        value = value.trim();

        if (value.startsWith("\"") && value.endsWith("\"")) {
            value = value.substring(1, value.length() - 1);
        }

        return value;
    }
}
