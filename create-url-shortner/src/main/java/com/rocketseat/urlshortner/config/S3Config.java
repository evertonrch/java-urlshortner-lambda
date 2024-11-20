package com.rocketseat.urlshortner.config;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

public class S3Config {

    private static final S3Client s3Client;

    static {
        s3Client = S3Client.builder()
                .region(Region.SA_EAST_1)
                .build();
    }

    public static S3Client getS3Client() {
        return S3Config.s3Client;
    }
}
