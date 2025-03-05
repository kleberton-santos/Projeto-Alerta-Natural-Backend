package com.br.alertanatural.util;

import com.br.alertanatural.config.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Obtém o token do cabeçalho da requisição
        String token = getTokenFromRequest(request);

        // Verifica se o token é válido
        if (token != null && jwtTokenProvider.validateToken(token)) {

            // Obtém o e-mail do usuário a partir do token
            String email = jwtTokenProvider.getEmailFromToken(token);

            // Cria um objeto CustomUserDetails representando o usuário autenticado
            CustomUserDetails userDetails = new CustomUserDetails(email);

            // Cria um objeto de autenticação do Spring Security
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

            // Associa os detalhes da requisição à autenticação
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // Define a autenticação no contexto de segurança do Spring Security
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // Continua o fluxo da requisição
        filterChain.doFilter(request, response);
    }

    /**
     * Extrai o token JWT do cabeçalho "Authorization" da requisição.
     * O token deve estar no formato "Bearer <token>".
     * @param request Objeto HttpServletRequest contendo os dados da requisição.
     * @return O token JWT extraído ou null se não estiver presente ou inválido.
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Retorna apenas o token, sem o prefixo "Bearer "
        }
        return null;
    }
}
