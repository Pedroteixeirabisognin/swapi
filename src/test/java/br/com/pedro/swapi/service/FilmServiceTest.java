package br.com.pedro.swapi.service;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.pedro.swapi.client.SwapiClient;
import br.com.pedro.swapi.dto.SwapiFilmResponse;
import br.com.pedro.swapi.dto.SwapiFilmsResponse;
import br.com.pedro.swapi.model.Film;
import br.com.pedro.swapi.repository.FilmMemoryRepository;

@ExtendWith(MockitoExtension.class)
class FilmServiceTest {

    @Mock
    private SwapiClient swapiClient;

    @Mock
    private FilmMemoryRepository repository;

    private FilmService filmService;

    @BeforeEach
    void setUp() {
        filmService = new FilmService(swapiClient, repository);
    }

    @Test
    void shouldLoadFilms() {

        SwapiFilmResponse swapiFilm = new SwapiFilmResponse(
                "A New Hope",
                4,
                "Descrição original",
                "George Lucas",
                "Gary Kurtz",
                "1977-05-25"
        );

        SwapiFilmsResponse response = new SwapiFilmsResponse(
                1,
                null,
                null,
                List.of(swapiFilm)
        );

        when(swapiClient.findAllFilms())
                .thenReturn(response);

        filmService.loadFilms();

        ArgumentCaptor<Film> filmCaptor =
                ArgumentCaptor.forClass(Film.class);

        verify(swapiClient).findAllFilms();

        verify(repository).save(filmCaptor.capture());

        Film savedFilm = filmCaptor.getValue();

        assertEquals(4, savedFilm.getEpisodeId());
        assertEquals("A New Hope", savedFilm.getTitle());
        assertEquals("Descrição original", savedFilm.getDescription());
        assertEquals("George Lucas", savedFilm.getDirector());
        assertEquals("Gary Kurtz", savedFilm.getProducer());
        assertEquals("1977-05-25", savedFilm.getReleaseDate());
        assertEquals(1, savedFilm.getVersion());
    }
}