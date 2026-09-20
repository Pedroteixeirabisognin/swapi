package br.com.pedro.swapi.client;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.pedro.swapi.dto.SwapiFilmsResponse;

@SpringBootTest
public class SwapiClientTest {

    @Autowired
    private SwapiClient swapiClient;

    @Test
    void shouldFindAllFilms() {

        SwapiFilmsResponse response = swapiClient.findAllFilms();

        assertNotNull(response);
        assertNotNull(response.results());
        assertFalse(response.results().isEmpty());
    }
}
