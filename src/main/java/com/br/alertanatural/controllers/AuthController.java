package com.br.alertanatural.controllers;

import com.br.alertanatural.DTOs.LoginDTO;
import com.br.alertanatural.services.LoginService;
import com.br.alertanatural.util.JwtResponse;
import com.br.alertanatural.util.JwtTokenProvider;
import com.br.alertanatural.util.RefreshTokenRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticação", description = "Endpoints relacionados à autenticação")
public class AuthController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    // Endpoint para login e geração de token
    @Operation(summary = "Login de usuário", description = "Realiza o login do usuário e retorna os tokens de acesso e refresh")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        String email = loginDTO.getEmail();
        String senha = loginDTO.getSenha();

        // Verifica se as credenciais estão corretas
        if (loginService.autenticar(email, senha)) {
            // Gera o access token JWT
            String accessToken = jwtTokenProvider.generateToken(email);

            // Gera o refresh token
            String refreshToken = jwtTokenProvider.generateRefreshToken(email);

            // Retorna os tokens no corpo da resposta
            return ResponseEntity.ok(new JwtResponse(accessToken, refreshToken));
        }

        // Retorna erro caso a autenticação falhe
        return ResponseEntity.status(401).body("Email ou senha inválidos");
    }

    // Endpoint para refresh token
    @Operation(summary = "Refresh Token", description = "Gera um novo token de acesso utilizando o refresh token")
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();

        // Valida o refresh token
        if (jwtTokenProvider.validateToken(refreshToken)) {
            // Recupera o email associado ao refresh token
            String email = jwtTokenProvider.getEmailFromToken(refreshToken);

            // Gera um novo access token com o mesmo email
            String newAccessToken = jwtTokenProvider.generateToken(email);

            // Retorna o novo access token e o refresh token
            return ResponseEntity.ok(new JwtResponse(newAccessToken, refreshToken));
        }

        // Retorna erro caso o refresh token seja inválido
        return ResponseEntity.status(401).body("Refresh Token inválido");
    }
}
