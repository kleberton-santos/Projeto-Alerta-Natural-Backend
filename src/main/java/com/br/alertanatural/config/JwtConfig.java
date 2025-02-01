package com.br.alertanatural.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    private String secret;
    private long expiration;
    private long refreshTokenExpiration;  // Nova propriedade para o tempo de expiração do refresh token

    @Value("${jwt.secret}")
    private String secretFromProperties;

    @Value("${jwt.expiration}")
    private long expirationFromProperties;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpirationFromProperties;

    @PostConstruct
    public void init() {
        this.secret = secretFromProperties;
        this.expiration = expirationFromProperties;
        this.refreshTokenExpiration = refreshTokenExpirationFromProperties;  // Inicializa o refresh token expiration
    }

    public String getSecret() {
        return secret;
    }

    public long getExpiration() {
        return expiration;
    }

    public long getRefreshTokenExpiration() {
        return refreshTokenExpiration;  // Método que agora retorna o tempo de expiração do refresh token
    }
}
