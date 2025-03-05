package com.br.alertanatural.util;

import com.br.alertanatural.config.JwtConfig;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Date;

/**
 * Classe responsável por gerar e validar tokens JWT para autenticação.
 */
@Component
public class JwtTokenProvider {

    private final String jwtSecret; // Chave secreta para assinar os tokens
    private final long jwtExpiration; // Tempo de expiração do Access Token
    private final long refreshTokenExpiration; // Tempo de expiração do Refresh Token

    /**
     * Construtor que recebe as configurações do JWT.
     * @param jwtConfig Objeto contendo as configurações do JWT.
     */
    @Autowired
    public JwtTokenProvider(JwtConfig jwtConfig) {
        this.jwtSecret = jwtConfig.getSecret();
        this.jwtExpiration = jwtConfig.getExpiration();
        this.refreshTokenExpiration = jwtConfig.getRefreshTokenExpiration();
    }

    /**
     * Gera um Access Token com tempo de expiração mais curto.
     * @param email Email do usuário autenticado.
     * @return Token JWT gerado.
     */
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    /**
     * Gera um Refresh Token com tempo de expiração mais longo.
     * @param email Email do usuário autenticado.
     * @return Refresh Token JWT gerado.
     */
    public String generateRefreshToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    /**
     * Extrai o email do usuário a partir do token JWT.
     * @param token Token JWT a ser analisado.
     * @return Email do usuário contido no token.
     */
    public String getEmailFromToken(String token) {
        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build();
        return parser.parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Valida o token JWT verificando se ele foi assinado corretamente e não está expirado.
     * @param token Token JWT a ser validado.
     * @return true se o token for válido, false caso contrário.
     */
    public boolean validateToken(String token) {
        try {
            JwtParser parser = Jwts.parserBuilder()
                    .setSigningKey(jwtSecret)
                    .build();
            parser.parseClaimsJws(token); // Verifica a validade do token
            return true;
        } catch (Exception e) {
            return false; // Retorna falso em caso de erro na validação
        }
    }
}