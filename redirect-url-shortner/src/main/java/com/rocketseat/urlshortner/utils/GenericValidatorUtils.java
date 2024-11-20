package com.rocketseat.urlshortner.utils;

import java.util.Objects;

public class GenericValidatorUtils {

    public static void valirUrlCode(String urlCode) {
        if(Objects.isNull(urlCode) || urlCode.isBlank())
            throw new IllegalArgumentException("parâmetro inválido");
    }

    public static boolean isValidTime(long urlTime) {
        long current = System.currentTimeMillis() / 1000;
        return urlTime < current;
    }
}
