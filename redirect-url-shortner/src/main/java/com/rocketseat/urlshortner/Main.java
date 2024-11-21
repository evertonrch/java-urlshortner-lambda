package com.rocketseat.urlshortner;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.rocketseat.urlshortner.config.S3Config;
import com.rocketseat.urlshortner.service.AWSService;
import com.rocketseat.urlshortner.utils.GenericValidatorUtils;
import com.rocketseat.urlshortner.utils.JsonUtils;
import com.rocketseat.urlshortner.utils.ResponseUtils;
import model.UrlData;

import java.io.InputStream;
import java.util.Map;

public class Main implements RequestHandler<Map<String, Object>, Map<String, Object>> {

    private final AWSService awsService = new AWSService(S3Config.getS3Client());
    private final String BUCKET_NAME = "my-url-shortner-bucket";

    @Override
    public Map<String, Object> handleRequest(Map<String, Object> request, Context context) {
        String path = (String) request.get("rawPath");
        String shortUrlCode = path.replace("/", "");

        GenericValidatorUtils.valirUrlCode(shortUrlCode);

        final String KEY = String.format("%s.json", shortUrlCode);

        var getObjectRequest = awsService.getObjectRequest(BUCKET_NAME, KEY);
        InputStream s3ObjectStream = awsService.objectToStream(getObjectRequest);
        UrlData urlData = JsonUtils.deserializationToUrlData(s3ObjectStream);

        if (GenericValidatorUtils.isValidTime(urlData.getExpirationTime())) {
            return ResponseUtils.ErrorClient();
        }

        return ResponseUtils.redirect(urlData);
    }
}