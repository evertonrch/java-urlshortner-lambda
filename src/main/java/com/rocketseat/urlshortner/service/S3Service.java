package com.rocketseat.urlshortner.service;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

public class S3Service {

    private final S3Client s3Client;

    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public void sendObject(String json, String bucketName, String key) throws S3Exception {
        s3Client.putObject(createRequest(bucketName, key), RequestBody.fromString(json));
    }

    private PutObjectRequest createRequest(String bucketName, String key) {
        return PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();
    }
}
