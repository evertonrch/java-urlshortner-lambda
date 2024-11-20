package com.rocketseat.urlshortner.utils;

import model.UrlData;

import java.util.HashMap;
import java.util.Map;

public class ResponseUtils {

    private static final Map<String, Object> response = new HashMap<>();

    public static Map<String, Object> ErrorClient() {
        response.put("statusCode", 412);
        response.put("body", "URL expirou");
        return response;
    }

    public static Map<String, Object> redirect(UrlData urlData) {
        response.put("statusCode", 302);
        response.put("headers", Map.of("Location", urlData.getOriginalUrl()));
        return response;
    }
}
