package com.rocketseat.urlshortner;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rocketseat.urlshortner.config.S3Config;
import com.rocketseat.urlshortner.model.UrlData;
import com.rocketseat.urlshortner.service.S3Service;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class Main implements RequestHandler<Map<String, Object>, Map<String, String>> {

    private final ObjectMapper mapper = new ObjectMapper();
    private final S3Service s3Service = new S3Service(S3Config.getS3Client());
    private final String BUCKET_NAME = "my-url-shortner-bucket";

    @Override
    public Map<String, String> handleRequest(final Map<String, Object> request, final Context context) {
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
        String shortUrlCode = UUID.randomUUID().toString().substring(0, 8);

        try {
            String urlDataJson = mapper.writeValueAsString(urlData);
            s3Service.sendObject(urlDataJson, BUCKET_NAME, shortUrlCode.concat(".json"));
        } catch (S3Exception ex) {
            throw new RuntimeException("erro ao enviar o objeto " + ex.getMessage(), ex);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("erro ao converter o objeto " + e.getMessage(), e);
        }

        return Map.of("code", shortUrlCode);
    }

    private UrlData createUrlData(Map<String, String> bodyMap) {
        return UrlData.builder()
                .originalUrl(bodyMap.get("originalUrl"))
                .expirationTime(Long.parseLong(bodyMap.get("expirationTime")))
                .build();
    }
}