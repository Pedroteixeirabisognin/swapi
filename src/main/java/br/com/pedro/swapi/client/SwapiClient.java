package br.com.pedro.swapi.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import br.com.pedro.swapi.dto.SwapiFilmsResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SwapiClient {

    private final RestClient restClient;

    public SwapiClient(
            @Value("${swapi.api.url}") String swapiUrl) {

        this.restClient = RestClient.builder()
                .baseUrl(swapiUrl)
                .build();
    }

    public SwapiFilmsResponse findAllFilms() {
        log.info("BUSCANDO FILMES NA SWAPI");

        return restClient
                .get()
                .uri("/films/")
                .retrieve()
                .body(SwapiFilmsResponse.class);
    }
}