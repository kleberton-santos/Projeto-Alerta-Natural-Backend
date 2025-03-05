package com.br.alertanatural.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;  // Importa a anotação para definir as informações da API
import io.swagger.v3.oas.annotations.info.Info;  // Importa a anotação para adicionar detalhes à documentação da API
import org.springframework.context.annotation.Configuration;

@Configuration // Define esta classe como uma classe de configuração do Spring
@OpenAPIDefinition(info = @Info(title = "Projeto alerta natural", version = "1.0", description = "Documentação da API com Spring Boot e JWT"))
// Configura a documentação da API usando Swagger, definindo o título, versão e descrição da API
public class SwaggerConfig {
}
