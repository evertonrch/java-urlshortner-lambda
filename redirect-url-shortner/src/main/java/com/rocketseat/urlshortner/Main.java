package com.rocketseat.urlshortner;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.UrlData;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Main implements RequestHandler<Map<String, Object>, Map<String, Object>> {

    private final S3Client s3Client = S3Client.builder().build();
    private final ObjectMapper mapper = new ObjectMapper();
    private final String BUCKET_NAME = "my-url-shortner-bucket";

    @Override
    public Map<String, Object> handleRequest(Map<String, Object> request, Context context) {
        String path = (String) request.get("rawPath");
        String shortUrlCode = path.replace("/", "");

        if (Objects.isNull(shortUrlCode) || shortUrlCode.isBlank())
            throw new IllegalArgumentException("parâmetro inválido");

        final String KEY = String.format("%s.json", shortUrlCode);

        var getObjectRequest = GetObjectRequest.builder()
                .bucket(BUCKET_NAME)
                .key(KEY)
                .build();

        InputStream s3ObjectStream = null;
        try {
            s3ObjectStream = s3Client.getObject(getObjectRequest);
        } catch (S3Exception ex) {
            throw new RuntimeException("Erro ao buscar dados do S3 " + ex.getMessage(), ex);
        }

        UrlData urlData = null;
        try {
            urlData = mapper.readValue(s3ObjectStream, UrlData.class);
        } catch (IOException ex) {
            throw new RuntimeException("Erro ao desserializar os dados " + ex.getMessage(), ex);
        }

        Map<String, Object> response = new HashMap<>();

        long current = System.currentTimeMillis() / 1000;
        if (urlData.getExpirationTime() < current) {
            response.put("statusCode", 412);
            response.put("body", "URL expirou");
            return response;
        }

        response.put("statusCode", 302);
        response.put("headers", Map.of("Location", urlData.getOriginalUrl()));
        return response;
    }
}