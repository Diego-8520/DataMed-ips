package com.clinica.medica.infrastructure.exceptions;

import com.clinica.medica.domain.exceptions.ReglaNegocioException;
import com.clinica.medica.domain.exceptions.RecursoNoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.time.Instant;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RecursoNoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError notFound(RecursoNoEncontradoException ex) {
        return error(HttpStatus.NOT_FOUND, List.of(ex.getMessage()));
    }

    @ExceptionHandler(ReglaNegocioException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ApiError businessRule(ReglaNegocioException ex) {
        return error(HttpStatus.UNPROCESSABLE_ENTITY, List.of(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError validation(MethodArgumentNotValidException ex) {
        List<String> messages = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();
        return error(HttpStatus.BAD_REQUEST, messages);
    }

    private ApiError error(HttpStatus status, List<String> messages) {
        return new ApiError(Instant.now(), status.value(), status.getReasonPhrase(), messages);
    }
}
