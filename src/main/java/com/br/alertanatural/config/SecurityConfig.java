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

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Nova forma de configurar CSRF, caso necessário
        http
                .csrf(csrf -> csrf.disable())  // Desabilita CSRF, se necessário

                // Novo método para configuração de autorização
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/auth/login", "/usuarios").permitAll()  // Permite acesso à rota de login
                        .anyRequest().authenticated()  // Outras rotas exigem autenticação
                )

                // Adicionando o filtro de autenticação JWT
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
