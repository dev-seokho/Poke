package com.noah.sns.poke.global.configuration

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders


@Configuration
class SwaggerConfig {
    @Bean
    fun openAPI(): OpenAPI = OpenAPI()
        .components(Components().addSecuritySchemes("Authorization", securitySchemaInfo()))
        .addSecurityItem(securityRequirementInfo())
        .info(apiInfo())

    private fun apiInfo() = Info()
        .title("Poke API")
        .description("Poke REST API")
        .version("1.0.0")

    private fun securitySchemaInfo(): SecurityScheme = SecurityScheme()
        .type(SecurityScheme.Type.HTTP)
        .scheme("bearer")
        .bearerFormat("Authorization")
        .`in`(SecurityScheme.In.HEADER)
        .name(HttpHeaders.AUTHORIZATION)

    private fun securityRequirementInfo(): SecurityRequirement = SecurityRequirement()
        .addList("Authorization")
}