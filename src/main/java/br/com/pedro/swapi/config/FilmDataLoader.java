package br.com.pedro.swapi.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import br.com.pedro.swapi.service.FilmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class FilmDataLoader implements ApplicationRunner {

    private final FilmService filmService;

    @Override
    public void run(ApplicationArguments args) {

        log.info("Iniciando carregamento dos filmes da SWAPI");

        filmService.loadFilms();

        log.info("Filmes carregados com sucesso");
    }
}