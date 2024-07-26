package br.janioofi.todolist.config.infra;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Swagger {

    @Bean
    public GroupedOpenApi publicApi(){
        return GroupedOpenApi.builder()
                .group("To Do List")
                .displayName("API")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("To Do List - API")
                        .description("Rest API")
                        .contact(new Contact()
                                .name("@janioofi")
                                .email("janioofi@gmail.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.linkedin.com/in/janioofi/")));
    }
}