package com.cairone.provider;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProviderClientCfg {

    @Value("${provider-url}")
    private String providerUrl;

    @Bean("clientProvider")
    public WebTarget getProviderClient() {
        return ClientBuilder.newClient().target(providerUrl);
        
    }
}
