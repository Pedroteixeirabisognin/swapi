package br.com.pedro.swapi.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(FilmNotFoundException.class)
        public ResponseEntity<ApiError> handleFilmNotFound(
                        FilmNotFoundException exception,
                        HttpServletRequest request) {

                HttpStatus status = HttpStatus.NOT_FOUND;

                ApiError error = new ApiError(
                                LocalDateTime.now(),
                                status.value(),
                                status.getReasonPhrase(),
                                exception.getMessage(),
                                request.getRequestURI());

                return ResponseEntity
                                .status(status)
                                .body(error);
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ApiError> handleValidationException(
                        MethodArgumentNotValidException exception,
                        HttpServletRequest request) {

                HttpStatus status = HttpStatus.BAD_REQUEST;

                String message = exception
                                .getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .findFirst()
                                .map(FieldError::getDefaultMessage)
                                .orElse("Dados inválidos");

                ApiError error = new ApiError(
                                LocalDateTime.now(),
                                status.value(),
                                status.getReasonPhrase(),
                                message,
                                request.getRequestURI());

                return ResponseEntity.status(status).body(error);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ApiError> handleInternalServerError(
                        Exception exception,
                        HttpServletRequest request) {

                HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

                ApiError error = new ApiError(
                                LocalDateTime.now(),
                                status.value(),
                                status.getReasonPhrase(),
                                "Ocorreu um erro interno no servidor.",
                                request.getRequestURI());

                return ResponseEntity
                                .status(status)
                                .body(error);
        }

}