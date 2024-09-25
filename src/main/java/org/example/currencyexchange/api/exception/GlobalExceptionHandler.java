package org.example.currencyexchange.api.exception;

import jakarta.persistence.EntityNotFoundException;
import org.example.currencyexchange.util.ExchangeApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

import static org.example.currencyexchange.util.ExchangeApiResponse.of;
import static org.springframework.http.HttpStatus.BAD_GATEWAY;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.ResponseEntity.badRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ExchangeApiResponse> handleEntityNotFoundException(EntityNotFoundException e) {
        return new ResponseEntity<>(of(e.getMessage()), NOT_FOUND);
    }

    @ExceptionHandler(JsonFromApiProcessingException.class)
    public ResponseEntity<ExchangeApiResponse> handleJsonFromApiProcessingException(JsonFromApiProcessingException e) {
        return new ResponseEntity<>(of(e.getMessage()), BAD_GATEWAY);
    }

    @ExceptionHandler(ExchangeException.class)
    public ResponseEntity<ExchangeApiResponse> handleExchangeException(ExchangeException e) {
        return new ResponseEntity<>(of(e.getMessage()), BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errors = e.getBindingResult().getAllErrors().stream()
                .collect(Collectors.toMap(
                        t -> t instanceof FieldError ? ((FieldError) t).getField() : t.getObjectName(),
                        err -> err.getDefaultMessage() != null ? err.getDefaultMessage() : "Invaid Format"));
        return badRequest().body(errors);
    }
}
