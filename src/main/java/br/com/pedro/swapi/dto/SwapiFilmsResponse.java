package br.com.pedro.swapi.dto;

import java.util.List;

public record SwapiFilmsResponse(
        Integer count,
        String next,
        String previous,
        List<SwapiFilmResponse> results
) {
}
