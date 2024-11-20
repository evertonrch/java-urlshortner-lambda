package com.rocketseat.urlshortner.config;

import software.amazon.awssdk.services.s3.S3Client;

public class S3Config {

    public static final S3Client client;

    static {
        client = S3Client.builder().build();
    }

    public static S3Client getS3Client() {
        return client;
    }
}
