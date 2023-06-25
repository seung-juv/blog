package com.seungju.blog.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Value("${springdoc.version}")
  private String version;

  @Value("${springdoc.title}")
  private String title;

  @Value("${springdoc.description}")
  private String description;

  @Value("${springdoc.summary}")
  private String summary;

  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI().components(new Components().addSecuritySchemes("jwt",
            new SecurityScheme().name("jwt").type(SecurityScheme.Type.HTTP).scheme("bearer")
                .bearerFormat("JWT")))
        .info(new Info().version(version).title(title).description(description).summary(summary))
        .addSecurityItem(new SecurityRequirement().addList("jwt"));
  }
}
