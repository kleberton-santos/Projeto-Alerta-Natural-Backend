package com.br.alertanatural.util;

import com.br.alertanatural.config.JwtConfig;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final String jwtSecret;
    private final long jwtExpiration;

    @Autowired
    public JwtTokenProvider(JwtConfig jwtConfig) {
        this.jwtSecret = jwtConfig.getSecret();
        this.jwtExpiration = jwtConfig.getExpiration();
    }

    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getEmailFromToken(String token) {
        JwtParser parser = Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build();
        return parser.parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            JwtParser parser = Jwts.parserBuilder()
                    .setSigningKey(jwtSecret)
                    .build();
            parser.parseClaimsJws(token);  // Verifica se o token é válido
            return true;
        } catch (Exception e) {
            return false;  // Caso de erro no parsing ou token inválido
        }
    }
}



