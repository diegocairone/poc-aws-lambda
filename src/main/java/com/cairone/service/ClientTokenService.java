package com.cairone.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.interfaces.RSAPublicKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cairone.AppRuntimeException;

import lombok.extern.slf4j.Slf4j;

/**
 * @see https://github.com/auth0/java-jwt
 * @author diego.cairone
 */
@Slf4j
@Service
public class ClientTokenService {

    @Value("${jwk-set-url}")
    private String jwkSetUrl;
    
    @Value("${required-roles}")
    private String[] roles;

    public DecodedJWT verify(String token) {
        DecodedJWT jwt = JWT.decode(token);
        verify(jwt);
        return jwt;
    }
    
    private void verify(DecodedJWT jwt) {
        try {
            
            JwkProvider provider = new UrlJwkProvider(new URL(jwkSetUrl));
            Jwk jwk = provider.get(jwt.getKeyId());
            
            Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) jwk.getPublicKey(), null);
            algorithm.verify(jwt);
            
            JWTVerifier verifier = JWT.require(algorithm)
                    .acceptLeeway(1)   //1 sec for nbf and iat
                    .acceptExpiresAt(5)   //5 secs for exp
                    .withArrayClaim("roles", roles)
                    .build();
            
            verifier.verify(jwt);
            
            
        } catch (
                TokenExpiredException | 
                SignatureVerificationException | 
                InvalidClaimException |
                MalformedURLException | 
                JwkException e) {
            
            log.error(e.getMessage());
            throw new AppRuntimeException(e.getMessage(), e);
        }
    }
}
