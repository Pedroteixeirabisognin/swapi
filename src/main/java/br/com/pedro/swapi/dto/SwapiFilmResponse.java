package br.com.pedro.swapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SwapiFilmResponse(

        String title,

        @JsonProperty("episode_id")
        Integer episodeId,

        @JsonProperty("opening_crawl")
        String openingCrawl,

        String director,

        String producer,

        @JsonProperty("release_date")
        String releaseDate
) {
}
