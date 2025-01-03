package com.rocketseat.urlshortner.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UrlData {

    private String originalUrl;
    private Long expirationTime;
}
