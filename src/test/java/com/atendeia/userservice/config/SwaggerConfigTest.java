package com.atendeia.userservice.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.junit.jupiter.api.Test;
import org.springdoc.core.models.GroupedOpenApi;

import static org.assertj.core.api.Assertions.assertThat;

class SwaggerConfigTest {

    private final SwaggerConfig swaggerConfig = new SwaggerConfig();

    @Test
    void deveCriarBeanOpenAPI_corretamenteConfigurado() {
        OpenAPI openAPI = swaggerConfig.customOpenAPI();

        assertThat(openAPI).isNotNull();

        Info info = openAPI.getInfo();
        assertThat(info.getTitle()).isEqualTo("User Service API");
        assertThat(info.getVersion()).isEqualTo("1.0");
        assertThat(info.getDescription()).contains("AtendeIA");

        SecurityRequirement securityRequirement = openAPI.getSecurity().get(0);
        assertThat(securityRequirement).containsKey("bearer-jwt");

        SecurityScheme scheme = openAPI.getComponents().getSecuritySchemes().get("bearer-jwt");
        assertThat(scheme.getType()).isEqualTo(SecurityScheme.Type.HTTP);
        assertThat(scheme.getScheme()).isEqualTo("bearer");
        assertThat(scheme.getBearerFormat()).isEqualTo("JWT");
        assertThat(scheme.getIn()).isEqualTo(SecurityScheme.In.HEADER);
        assertThat(scheme.getName()).isEqualTo("Authorization");
    }

    @Test
    void deveCriarGroupedOpenApi_corretamente() {
        GroupedOpenApi groupedOpenApi = swaggerConfig.userApi();

        assertThat(groupedOpenApi).isNotNull();
        assertThat(groupedOpenApi.getGroup()).isEqualTo("user");
        assertThat(groupedOpenApi.getPathsToMatch()).containsExactly("/api/usuarios/**");
    }
}
