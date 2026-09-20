package br.com.pedro.swapi.exception;

public class FilmNotFoundException extends RuntimeException {

    public FilmNotFoundException(Integer episodeId) {
        super("Filme não encontrado para o episodeId: " + episodeId);
    }
}
