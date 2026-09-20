package br.com.pedro.swapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pedro.swapi.dto.UpdateDescriptionRequest;
import br.com.pedro.swapi.model.Film;
import br.com.pedro.swapi.service.FilmService;
import jakarta.validation.Valid;
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

    @GetMapping("/{episodeId}")
    public ResponseEntity<Film> findById(
            @PathVariable Integer episodeId) {

        return ResponseEntity.ok(
                filmService.findById(episodeId));
    }

    @PatchMapping("/{episodeId}/description")
    public ResponseEntity<Film> updateDescription(
            @PathVariable Integer episodeId,
            @Valid @RequestBody UpdateDescriptionRequest request) {

        Film film = filmService.updateDescription(
                episodeId,
                request.description()
        );

        return ResponseEntity.ok(film);
    }
}
