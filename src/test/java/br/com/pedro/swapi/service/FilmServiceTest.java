package br.com.pedro.swapi.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import br.com.pedro.swapi.exception.FilmNotFoundException;
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

    @Test
    void shouldFindAllFilmsOrderedByEpisodeId() {

        Film episode4 = createFilm(
                4,
                "A New Hope",
                "Descrição episódio 4"
        );

        Film episode1 = createFilm(
                1,
                "The Phantom Menace",
                "Descrição episódio 1"
        );

        Film episode3 = createFilm(
                3,
                "Revenge of the Sith",
                "Descrição episódio 3"
        );

        when(repository.findAll())
                .thenReturn(List.of(
                        episode4,
                        episode1,
                        episode3
                ));

        List<Film> result = filmService.findAll();

        assertEquals(3, result.size());

        assertEquals(1, result.get(0).getEpisodeId());
        assertEquals(3, result.get(1).getEpisodeId());
        assertEquals(4, result.get(2).getEpisodeId());

        verify(repository).findAll();
    }

    @Test
    void shouldFindFilmById() {

        Film film = createFilm(
                4,
                "A New Hope",
                "Descrição original"
        );

        when(repository.findById(4))
                .thenReturn(Optional.of(film));

        Film result = filmService.findById(4);

        assertEquals(4, result.getEpisodeId());
        assertEquals("A New Hope", result.getTitle());
        assertEquals("Descrição original", result.getDescription());

        verify(repository).findById(4);
    }

    @Test
    void shouldThrowExceptionWhenFilmDoesNotExist() {

        when(repository.findById(9999))
                .thenReturn(Optional.empty());

        FilmNotFoundException exception =
                assertThrows(
                        FilmNotFoundException.class,
                        () -> filmService.findById(9999)
                );

        assertEquals(
                "Filme não encontrado para o episodeId: 9999",
                exception.getMessage()
        );

        verify(repository).findById(9999);
    }

    @Test
    void shouldUpdateDescription() {

        Film updatedFilm = createFilm(
                4,
                "A New Hope",
                "Nova descrição"
        );

        updatedFilm.setVersion(2);

        when(repository.updateDescription(
                4,
                "Nova descrição"
        )).thenReturn(updatedFilm);

        Film result = filmService.updateDescription(
                4,
                "Nova descrição"
        );

        assertEquals(4, result.getEpisodeId());
        assertEquals("Nova descrição", result.getDescription());
        assertEquals(2, result.getVersion());

        verify(repository).updateDescription(
                4,
                "Nova descrição"
        );
    }

    private Film createFilm(
            Integer episodeId,
            String title,
            String description) {

        return new Film(
                episodeId,
                title,
                description,
                "George Lucas",
                "Producer",
                "2000-01-01",
                1
        );
    }
}