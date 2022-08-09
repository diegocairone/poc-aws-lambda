package com.cairone.service;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cairone.AppRuntimeException;
import com.cairone.provider.ProviderRequest;
import com.cairone.provider.ProviderResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AccessTokenProviderService {

    @Autowired
    private WebTarget clientProvider;
    
    @Value("${grant-type}") private String grantType;
    @Value("${client-id}") private String clientId;
    @Value("${assertion}") private String assertion;
    @Value("${scope}") private String scope;
    
    public String create() {
        
        ObjectMapper mapper = new ObjectMapper();
        
        ProviderRequest request = ProviderRequest.builder()
                .grantType(grantType)
                .clientId(clientId)
                .assertion(assertion)
                .scope(scope)
                .build();

        Response response = clientProvider
                .queryParam("grant_type", request.getGrantType())
                .queryParam("client_id", request.getClientId())
                .queryParam("assertion", request.getAssertion())
                .queryParam("scope", request.getScope())
                .request(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
                .post(Entity.entity(
                        request.getEncodedData(), MediaType.APPLICATION_FORM_URLENCODED_TYPE));
        
        if (response.getStatus() == Status.OK.getStatusCode()) {
            String responseBody = response.readEntity(String.class);
            
            try {
                ProviderResponse providerResponse = 
                        mapper.readValue(responseBody, ProviderResponse.class);
                return providerResponse.getAccessToken();
                
            } catch (JsonProcessingException e) {
                throw new AppRuntimeException(
                        e, "Could not acquire an access token - %s", e.getMessage());
            }
            
        } else {
            throw new AppRuntimeException("Could not acquire an access token");
        }
    }
}
