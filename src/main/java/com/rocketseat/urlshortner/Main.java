package com.rocketseat.urlshortner;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rocketseat.urlshortner.model.UrlData;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Main implements RequestHandler<Map<String, Object>, Map<String, String>> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Map<String, String> handleRequest(Map<String, Object> request, Context context) {
        String body = (String) request.get("body");

        if (Objects.isNull(body) || body.isBlank())
            throw new RuntimeException("Nenhum corpo na requisição");

        Map<String, String> bodyMap;
        try {
            bodyMap = mapper.readValue(body, Map.class);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException("Erro ao processar o JSON " + ex.getMessage());
        } catch (Exception ex) {
            throw new RuntimeException("Erro inesperado " + ex.getMessage());
        }

        UrlData urlData = createUrlData(bodyMap);
        return Map.of("code", urlData.getShortUrlCode());
    }

    private UrlData createUrlData(Map<String, String> bodyMap) {
        return UrlData.builder()
                .originalUrl(bodyMap.get("originalUrl"))
                .expirationTime(bodyMap.get("expirationTime"))
                .shortUrlCode(UUID.randomUUID().toString().substring(0, 8))
                .build();
    }
}