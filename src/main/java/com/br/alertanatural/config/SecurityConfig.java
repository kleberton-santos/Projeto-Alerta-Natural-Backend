package com.br.alertanatural.config;

import com.br.alertanatural.util.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // Define esta classe como uma classe de configuração do Spring
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;  // Injeta o filtro de autenticação JWT

    @Bean // Define o método como um bean que será gerenciado pelo Spring
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desativa a proteção CSRF, já que estamos usando autenticação baseada em token
                .authorizeHttpRequests(authz -> authz
                        // Permite acesso público a determinadas rotas (ex: Swagger, login, refresh token, etc)
                        .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/api/auth/login", "/usuarios", "/api/auth/refresh-token", "/api/auth/oauth2/**", "/**").permitAll()
                        .anyRequest().authenticated()  // Exige autenticação para qualquer outra requisição
                )
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login") // Define a página de login personalizada
                        .defaultSuccessUrl("/api/auth/oauth2/google", true) // Redireciona para o endpoint após login bem-sucedido
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);  // Adiciona o filtro JWT antes do filtro padrão de autenticação de usuário e senha

        return http.build();  // Retorna a configuração do filtro de segurança
    }

    @Bean // Define o método como um bean para o PasswordEncoder
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Usamos BCrypt para codificar senhas
    }
}
