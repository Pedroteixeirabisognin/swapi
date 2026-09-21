package br.com.pedro.swapi.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import br.com.pedro.swapi.dto.SwapiFilmsResponse;
import br.com.pedro.swapi.exception.SwapiUnavailableException;
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

        log.info("Buscando filmes na SWAPI");

        try {
            return restClient
                    .get()
                    .uri("/films/")
                    .retrieve()
                    .body(SwapiFilmsResponse.class);

        } catch (RestClientException exception) {

            log.error(
                    "Não foi possível carregar os filmes da SWAPI",
                    exception
            );

            throw new SwapiUnavailableException(
                    "SWAPI indisponível no momento",
                    exception
            );
        }
    }
}