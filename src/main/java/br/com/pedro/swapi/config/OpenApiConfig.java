package br.com.pedro.swapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI swapiOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Star Wars Films API")
                        .description(
                                "API REST para consulta e atualização " +
                                "dos filmes da saga Star Wars."
                        )
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Pedro Teixeira Bisognin")
                                .email("pedroteixeirabisognin@gmail.com")
                                .url("https://github.com/pedroteixeirabisognin")
                        )
                );
    }
}