package com.cairone.provider;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProviderRequest {

    private final String grantType;
    private final String clientId;
    private final String assertion;
    private final String scope;
    

    public String getEncodedData () {
        return String.format("?grant_type=%1s&client_id=%2s&scope=%3s&assertion=%4s",
                encodeParam(grantType),
                encodeParam(clientId),
                encodeParam(scope),
                encodeParam(assertion)
        );
    }

    private String encodeParam (String param) {
        return URLEncoder.encode(param, StandardCharsets.UTF_8);
    }
}
