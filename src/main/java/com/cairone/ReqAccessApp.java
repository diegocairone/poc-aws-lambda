package com.cairone;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.cairone.service.AccessTokenProviderService;
import com.cairone.service.ClientTokenService;

@SpringBootApplication
public class ReqAccessApp {

	public static void main(String[] args) {
		SpringApplication.run(ReqAccessApp.class, args);
	}

	// AWS handler: org.springframework.cloud.function.adapter.aws.FunctionInvoker
	@Bean
	public Function<String, String> requestAccessToken() {
	    return token -> {
	        tokenService.verify(token);
	        return providerService.create();
	    };
	}
	
	@Autowired
    private ClientTokenService  tokenService;
	
	@Autowired
	private AccessTokenProviderService providerService;
}
