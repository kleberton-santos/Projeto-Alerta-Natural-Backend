package com.br.alertanatural.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Projeto alerta natural", version = "1.0", description = "Documentação da API com Spring Boot e JWT"))
public class SwaggerConfig {
}