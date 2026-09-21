package br.com.pedro.swapi.exception;

public class SwapiUnavailableException extends RuntimeException {

    public SwapiUnavailableException(
            String message,
            Throwable cause) {

        super(message, cause);
    }
}