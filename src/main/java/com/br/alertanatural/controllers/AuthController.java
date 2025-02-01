package com.br.alertanatural.controllers;

import com.br.alertanatural.DTOs.LoginDTO;
import com.br.alertanatural.services.LoginService;
import com.br.alertanatural.util.JwtResponse;
import com.br.alertanatural.util.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    // Endpoint para login e geração de token
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        String email = loginDTO.getEmail();
        String senha = loginDTO.getSenha();

        // Verifica se as credenciais estão corretas
        if (loginService.autenticar(email, senha)) {
            // Gera o token JWT
            String token = jwtTokenProvider.generateToken(email);
            // Retorna o token no corpo da resposta
            return ResponseEntity.ok(new JwtResponse(token));
        }

        // Retorna erro caso a autenticação falhe
        return ResponseEntity.status(401).body("Email ou senha inválidos");
    }
}
