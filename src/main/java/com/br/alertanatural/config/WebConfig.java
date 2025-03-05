package com.br.alertanatural.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // Define esta classe como uma classe de configuração do Spring
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Configura o mapeamento de recursos estáticos para que arquivos em "file:/C:/foto-alerta/" sejam acessíveis através do caminho "/fotos/**"
        registry.addResourceHandler("/fotos/**")
                .addResourceLocations("file:/C:/foto-alerta/");  // Define o diretório onde as fotos estão localizadas
    }
}
