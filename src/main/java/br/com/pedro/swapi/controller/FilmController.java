package br.com.pedro.swapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pedro.swapi.model.Film;
import br.com.pedro.swapi.service.FilmService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/films")
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;

    @GetMapping
    public ResponseEntity<List<Film>> findAll() {
        return ResponseEntity.ok(filmService.findAll());
    }
}
