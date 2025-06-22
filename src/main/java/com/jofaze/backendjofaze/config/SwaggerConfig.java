package com.jofaze.backendjofaze.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(
            new Info()
                .title("API Administración de Hotel")
                .version("1.0")
                .description("El objetivo de esta API es gestionar reservas y administración de un hotel")
        );
    }
}