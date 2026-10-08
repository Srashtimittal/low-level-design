package com.conceptcoding.interviewquestions.meetingscheduler.api;

import java.util.HashMap;
import java.util.Map;

public final class SimpleJson {
    private SimpleJson() {}

    public static Map<String, String> parse(String json) {
        Map<String, String> map = new HashMap<>();
        if (json == null) return map;

        String body = json.trim();
        if (body.startsWith("{")) body = body.substring(1);
        if (body.endsWith("}")) body = body.substring(0, body.length() - 1);
        if (body.trim().isEmpty()) return map;

        for (String pair : body.split(",")) {
            String[] parts = pair.split(":", 2);
            if (parts.length != 2) continue;
            map.put(strip(parts[0].trim()), strip(parts[1].trim()));
        }
        return map;
    }

    private static String strip(String value) {
        if (value.startsWith("\"") && value.endsWith("\"")) {
            return value.substring(1, value.length() - 1);
        }
        return value;
    }
}
