package com.walefy.restaurantorders.security;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.stereotype.Component;

@Component
public class DocAuthCustomizer implements OpenApiCustomizer {
  public final String schemeName = "Bearer Auth";

  @Override
  public void customise(OpenAPI openApi) {
    SecurityScheme securityScheme = new SecurityScheme()
      .type(SecurityScheme.Type.HTTP)
      .scheme("bearer")
      .bearerFormat("JWT");

    openApi.getComponents().addSecuritySchemes(schemeName, securityScheme);
    openApi.addSecurityItem(new SecurityRequirement().addList(schemeName));
  }
}