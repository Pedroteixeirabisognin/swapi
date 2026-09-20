package br.com.pedro.swapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Film {

    private Integer episodeId;
    private String title;
    private String description;
    private String director;
    private String producer;
    private String releaseDate;
    private Integer version;

}