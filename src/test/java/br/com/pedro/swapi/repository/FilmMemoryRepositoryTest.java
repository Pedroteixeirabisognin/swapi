package br.com.pedro.swapi.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.pedro.swapi.exception.FilmNotFoundException;
import br.com.pedro.swapi.model.Film;

class FilmMemoryRepositoryTest {

    private FilmMemoryRepository repository;

    @BeforeEach
    void setUp() {
        repository = new FilmMemoryRepository();
    }

    @Test
    void shouldSaveAndFindFilmById() {
        Film film = createFilm();

        repository.save(film);

        Optional<Film> result = repository.findById(4);

        assertTrue(result.isPresent());
        assertEquals(4, result.get().getEpisodeId());
        assertEquals("A New Hope", result.get().getTitle());
        assertEquals("Descrição original", result.get().getDescription());
        assertEquals(1, result.get().getVersion());
    }

    @Test
    void shouldReturnEmptyWhenFilmDoesNotExist() {
        Optional<Film> result = repository.findById(99999);

        assertTrue(result.isEmpty());
    }

    @Test
    void shouldFindAllFilms() {
        Film film1 = createFilm();

        Film film2 = new Film(
                5,
                "The Empire Strikes Back",
                "Descrição do episódio 5",
                "Irvin Kershner",
                "Gary Kurtz",
                "1980-05-17",
                1
        );

        repository.save(film1);
        repository.save(film2);

        List<Film> result = repository.findAll();

        assertEquals(2, result.size());
    }

    @Test
    void shouldUpdateDescriptionAndIncrementVersion() {
        Film film = createFilm();

        repository.save(film);

        Film updatedFilm = repository.updateDescription(
                4,
                "Nova descrição"
        );

        assertEquals("Nova descrição", updatedFilm.getDescription());
        assertEquals(2, updatedFilm.getVersion());
    }

    @Test
    void shouldIncrementVersionForEveryDescriptionUpdate() {
        Film film = createFilm();

        repository.save(film);

        repository.updateDescription(4, "Primeira alteração");
        Film updatedFilm =
                repository.updateDescription(4, "Segunda alteração");

        assertEquals("Segunda alteração", updatedFilm.getDescription());
        assertEquals(3, updatedFilm.getVersion());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNonExistingFilm() {
        FilmNotFoundException exception = assertThrows(
                FilmNotFoundException.class,
                () -> repository.updateDescription(
                        999,
                        "Nova descrição"
                )
        );

        assertEquals(
                "Filme não encontrado para o episodeId: 999",
                exception.getMessage()
        );
    }

    private Film createFilm() {
        return new Film(
                4,
                "A New Hope",
                "Descrição original",
                "George Lucas",
                "Gary Kurtz",
                "1977-05-25",
                1
        );
    }
}