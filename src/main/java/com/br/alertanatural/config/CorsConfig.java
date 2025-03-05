package com.br.alertanatural.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // Indica que esta classe é uma classe de configuração do Spring
public class CorsConfig {

    @Bean // Define este método como um bean que será gerenciado pelo Spring
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // Configura o CORS para permitir acessos do frontend
                registry.addMapping("/**") // Permite todas as requisições para qualquer endpoint
                        .allowedOrigins("http://localhost:5173") // Permite somente o frontend no localhost:5173
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Permite esses métodos HTTP
                        .allowedHeaders("*") // Permite todos os cabeçalhos de requisição
                        .allowCredentials(true); // Permite o envio de credenciais (como cookies) nas requisições
            }
        };
    }
}
