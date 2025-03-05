package com.br.alertanatural.controllers;

import com.br.alertanatural.DTOs.LoginDTO;
import com.br.alertanatural.models.Usuarios;
import com.br.alertanatural.services.LoginService;
import com.br.alertanatural.services.UsuarioService;
import com.br.alertanatural.util.JwtResponse;
import com.br.alertanatural.util.JwtTokenProvider;
import com.br.alertanatural.util.RefreshTokenRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticação", description = "Endpoints relacionados à autenticação")
public class AuthController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

            // Recupera os dados do usuário para enviar junto com os tokens
            Optional<Usuarios> usuario = usuarioService.listarUsuariosPorEmail(email);
            if (usuario.isPresent()) {
                Usuarios u = usuario.get();
                JwtResponse response = new JwtResponse(accessToken, refreshToken, u.getIdusuario(), u.getNome(), u.getFoto());
                return ResponseEntity.ok(response);
            }
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

    // Endpoint para autenticação via Google OAuth2
    @Operation(summary = "Login com Google", description = "Realiza o login do usuário utilizando o Google OAuth2")
    @GetMapping("/oauth2/google")
    public void googleLogin(OAuth2AuthenticationToken oauthToken, HttpServletResponse response) throws IOException {
        // Extrai o email do usuário autenticado pelo Google
        String email = oauthToken.getPrincipal().getAttribute("email");

        // Verifica se o usuário já existe no sistema
        Optional<Usuarios> usuarioOptional = usuarioService.listarUsuariosPorEmail(email);
        Usuarios usuario;

        if (usuarioOptional.isPresent()) {
            // Usuário já existe, recupera os dados
            usuario = usuarioOptional.get();
        } else {
            // Cria um novo usuário com os dados do Google
            usuario = new Usuarios();
            usuario.setEmail(email);
            usuario.setNome(oauthToken.getPrincipal().getAttribute("name"));
            usuario.setFoto(oauthToken.getPrincipal().getAttribute("picture"));
            usuario.setSenha(null); // Define a senha como null (ou um valor padrão)
            usuario = usuarioService.criarUsuario(usuario);
        }

        // Gera o token JWT
        String accessToken = jwtTokenProvider.generateToken(email);

        // Armazena os dados do usuário no localStorage (via frontend)
        String userData = String.format(
                "{\"nome\":\"%s\",\"foto\":\"%s\",\"id\":%d}",
                usuario.getNome(),
                usuario.getFoto(),
                usuario.getIdusuario()
        );

        // Redireciona para a página de feed com o token e os dados do usuário como parâmetros
        response.sendRedirect("http://localhost:5173/feed?token=" + accessToken + "&user=" + URLEncoder.encode(userData, "UTF-8"));
    }

    // Endpoint para solicitar redefinição de senha
    @Operation(summary = "Solicitar redefinição de senha", description = "Envia um e-mail com um link para redefinir a senha")
    @PostMapping("/esqueci-senha")
    public ResponseEntity<?> solicitarRedefinicaoSenha(@RequestParam String email) {
        Optional<Usuarios> usuarioOpt = usuarioService.listarUsuariosPorEmail(email);

        if (usuarioOpt.isPresent()) {
            Usuarios usuario = usuarioOpt.get();
            String token = jwtTokenProvider.generateToken(email); // Gera um token de redefinição

            // Salva o token no banco de dados
            usuario.setTokenRedefinicaoSenha(token);
            usuarioService.atualizarUsuario(usuario);

            // Envia o e-mail com o link de redefinição
            String resetLink = "http://localhost:5173/redefinir-senha?token=" + token;
            String mensagem = "Para redefinir sua senha, clique no link abaixo:\n" + resetLink;

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(email);
            mailMessage.setSubject("Redefinição de Senha");
            mailMessage.setText(mensagem);
            mailSender.send(mailMessage);

            return ResponseEntity.ok("E-mail de redefinição de senha enviado com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("E-mail não encontrado.");
        }
    }

    // Endpoint para redefinir a senha
    @Operation(summary = "Redefinir senha", description = "Redefine a senha do usuário com base no token de redefinição")
    @PostMapping("/redefinir-senha")
    public ResponseEntity<?> redefinirSenha(@RequestParam String token, @RequestParam String novaSenha) {
        // Valida o token
        if (jwtTokenProvider.validateToken(token)) {
            String email = jwtTokenProvider.getEmailFromToken(token);

            Optional<Usuarios> usuarioOpt = usuarioService.listarUsuariosPorEmail(email);

            if (usuarioOpt.isPresent()) {
                Usuarios usuario = usuarioOpt.get();

                // Verifica se o token de redefinição é o mesmo que foi salvo no banco
                if (token.equals(usuario.getTokenRedefinicaoSenha())) {
                    // Atualiza a senha
                    usuario.setSenha(passwordEncoder.encode(novaSenha));
                    usuario.setTokenRedefinicaoSenha(null); // Limpa o token de redefinição
                    usuarioService.atualizarUsuario(usuario);

                    return ResponseEntity.ok("Senha redefinida com sucesso.");
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token inválido.");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Token inválido ou expirado.");
        }
    }
}
