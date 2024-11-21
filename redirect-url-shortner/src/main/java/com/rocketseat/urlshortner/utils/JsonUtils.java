package com.rocketseat.urlshortner.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.UrlData;

import java.io.IOException;
import java.io.InputStream;

public class JsonUtils {

    private final static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
    }

    public static UrlData deserializationToUrlData(InputStream inputObject) {
        UrlData urlData = null;
        try {
            urlData = mapper.readValue(inputObject, UrlData.class);
        } catch (IOException ex) {
            throw new RuntimeException("Erro ao desserializar os dados " + ex.getMessage(), ex);
        }
        return urlData;
    }
}
