package com.rocketseat.urlshortner.service;

import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.InputStream;

public class AWSService {

    private S3Client s3Client;

    public AWSService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public GetObjectRequest getObjectRequest(String bucketName, String key) {
        return GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
    }

    public InputStream objectToStream(GetObjectRequest object) {
        InputStream s3ObjectStream = null;
        try {
            s3ObjectStream = s3Client.getObject(object);
        } catch (S3Exception ex) {
            throw new RuntimeException("Erro ao buscar dados do S3 " + ex.getMessage(), ex);
        }
        return s3ObjectStream;
    }
}
