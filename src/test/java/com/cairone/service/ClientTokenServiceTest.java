package com.cairone.service;

import static org.assertj.core.api.Assertions.assertThatNoException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class ClientTokenServiceTest {
    
    @Value("${token-for-testing}")
    private String token;
    
    @Autowired
    private ClientTokenService tokenService;
    
    @Test
    public void givenJwt_whenIsValid_thenNoExceptions() {
        assertThatNoException().isThrownBy(() -> tokenService.verify(token));
    }
}
