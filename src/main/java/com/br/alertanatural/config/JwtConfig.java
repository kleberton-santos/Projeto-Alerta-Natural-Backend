package com.br.alertanatural.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration // Indica que esta é uma classe de configuração do Spring
public class JwtConfig {

    private String secret;  // Armazena o segredo usado para assinar os tokens JWT
    private long expiration;  // Armazena o tempo de expiração do access token
    private long refreshTokenExpiration;  // Nova propriedade para o tempo de expiração do refresh token

    @Value("${jwt.secret}")  // Injeta o valor da propriedade 'jwt.secret' do arquivo de configuração
    private String secretFromProperties;

    @Value("${jwt.expiration}")  // Injeta o valor da propriedade 'jwt.expiration' do arquivo de configuração
    private long expirationFromProperties;

    @Value("${jwt.refresh-token-expiration}")  // Injeta o valor da propriedade 'jwt.refresh-token-expiration' do arquivo de configuração
    private long refreshTokenExpirationFromProperties;

    @PostConstruct  // Método executado após a inicialização do bean para configurar os valores
    public void init() {
        this.secret = secretFromProperties;  // Inicializa o segredo com o valor da propriedade
        this.expiration = expirationFromProperties;  // Inicializa o tempo de expiração do access token
        this.refreshTokenExpiration = refreshTokenExpirationFromProperties;  // Inicializa o tempo de expiração do refresh token
    }

    public String getSecret() {
        return secret;  // Retorna o segredo para assinatura dos tokens
    }

    public long getExpiration() {
        return expiration;  // Retorna o tempo de expiração do access token
    }

    public long getRefreshTokenExpiration() {
        return refreshTokenExpiration;  // Retorna o tempo de expiração do refresh token
    }
}
