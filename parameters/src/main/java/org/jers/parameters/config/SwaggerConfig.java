package org.jers.parameters.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.jers.parameters.utils.SystemConstants.PARAMETERS_PATH;

@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("springshop-public")
                .pathsToMatch(PARAMETERS_PATH + "/**")
                .build();
    }

    @Bean
    public OpenAPI springShopOpenAPI() {
        Contact contact = new Contact();
        contact.setName("Julian Enrique Rodriguez");
        contact.setEmail("julianand2009@hotmail.com");
        contact.setUrl("https://www.linkedin.com/in/julianerodriguezs/");
        return new OpenAPI()
                .info(new Info()
                        .title("Parameters")
                        .description("This is a microservice with several APIs to access parametric tables for different projects.")
                        .version("1.0")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org"))
                        .contact(contact)
                )
                .externalDocs(new ExternalDocumentation()
                        .description("SpringShop Wiki Documentation")
                        .url("https://springshop.wiki.github.org/docs")
                );
    }
}