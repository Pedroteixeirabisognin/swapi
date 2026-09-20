package br.com.pedro.swapi.repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import br.com.pedro.swapi.exception.FilmNotFoundException;
import br.com.pedro.swapi.model.Film;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class FilmMemoryRepository {

    private final ConcurrentHashMap<Integer, Film> films = new ConcurrentHashMap<>();

    public void save(Film film) {
        log.info("Salvando filme: {}", film.getTitle());
        films.put(film.getEpisodeId(), film);
    }

    public List<Film> findAll() {
        log.info("Listando todos os filmes");
        return List.copyOf(films.values());
    }

    public Optional<Film> findById(Integer episodeId) {
        log.info("Buscando filme com ID: {}", episodeId);
        return Optional.ofNullable(films.get(episodeId));
    }

    public Film updateDescription(
            Integer episodeId,
            String newDescription) {

        log.info("Atualizando descrição do filme com ID: {}", episodeId);

        return films.compute(episodeId, (id, film) -> {

            if (film == null) {
                throw new FilmNotFoundException(episodeId);
            }

            film.setDescription(newDescription);
            film.setVersion(film.getVersion() + 1);

            return film;
        });
    }
}
