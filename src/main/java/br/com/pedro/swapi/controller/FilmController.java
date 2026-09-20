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
import br.com.pedro.swapi.exception.ApiError;
import br.com.pedro.swapi.model.Film;
import br.com.pedro.swapi.service.FilmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(
    name = "Films",
    description = "Operações relacionadas aos filmes da saga Star Wars"
)
@RestController
@RequestMapping("/api/films")
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;

    @Operation(
        summary = "Lista todos os filmes",
        description = "Retorna os filmes da saga Star Wars carregados em memória."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Filmes encontrados"
    )
    @GetMapping
    public ResponseEntity<List<Film>> findAll() {
        return ResponseEntity.ok(filmService.findAll());
    }

    @Operation(
            summary = "Busca um filme pelo episódio",
            description = "Retorna os dados de um filme a partir do número do episódio."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Filme encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Film.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Filme não encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
    @GetMapping("/{episodeId}")
    public ResponseEntity<Film> findById(
            @PathVariable Integer episodeId) {

        return ResponseEntity.ok(
                filmService.findById(episodeId));
    }

    
    @Operation(
            summary = "Altera a descrição de um filme",
            description = "Atualiza a descrição do filme e incrementa sua versão."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Descrição atualizada com sucesso",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Film.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Descrição inválida",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Filme não encontrado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiError.class)
                    )
            )
    })
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
