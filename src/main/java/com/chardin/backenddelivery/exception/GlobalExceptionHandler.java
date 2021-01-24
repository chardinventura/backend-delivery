package com.chardin.backenddelivery.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException) {

        HttpStatus notFound = HttpStatus.NOT_FOUND;

        ResourceNotFoundResponse body = new ResourceNotFoundResponse();
        body.setError(notFound.getReasonPhrase());
        body.setMessage(resourceNotFoundException.getMessage());
        body.setStatus(notFound.value());
        body.setTimeStamp(LocalDateTime.now());

        return new ResponseEntity<>(body, notFound);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity handleValidationException(ValidationException validationException) {

        HttpStatus status = HttpStatus.BAD_REQUEST;

        ValidationResponse body = new ValidationResponse();
        body.setMessage(status.getReasonPhrase());
        body.setStatus(status.value());
        body.setTimeStamp(LocalDateTime.now());

        Map<String, String> errors = validationException.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        e -> e.getField(),
                        e -> e.getDefaultMessage()));

        body.setErrors(errors);

        return new ResponseEntity<>(body, status);
    }
}
