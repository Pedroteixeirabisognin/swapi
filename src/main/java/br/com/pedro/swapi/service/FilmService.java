package br.com.pedro.swapi.service;

import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.pedro.swapi.client.SwapiClient;
import br.com.pedro.swapi.dto.SwapiFilmsResponse;
import br.com.pedro.swapi.model.Film;
import br.com.pedro.swapi.repository.FilmMemoryRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final SwapiClient swapiClient;
    private final FilmMemoryRepository repository;

    public void loadFilms() {

        SwapiFilmsResponse response = swapiClient.findAllFilms();

        response.results().forEach(swapiFilm -> {

            Film film = new Film(
                    swapiFilm.episodeId(),
                    swapiFilm.title(),
                    swapiFilm.openingCrawl(),
                    swapiFilm.director(),
                    swapiFilm.producer(),
                    swapiFilm.releaseDate(),
                    1);

            repository.save(film);
        });
    }

    public List<Film> findAll() {
        return repository.findAll()
                .stream()
                .sorted(Comparator.comparing(Film::getEpisodeId))
                .toList();
    }
}