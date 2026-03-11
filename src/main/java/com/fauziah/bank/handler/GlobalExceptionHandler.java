package com.fauziah.bank.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Validation
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseHandler> handleValidationException(
        MethodArgumentNotValidException e) {
        logger.error("JSON parsing error", e);

        Map<String,String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        ResponseHandler response = new ResponseHandler();
        response.setStatus("Failed");
        response.setError("Validation error");
        response.setData(errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ResponseHandler> handleInvalidFormat(InvalidFormatException e) {

        Map<String,String> errors = new HashMap<>();

        String fieldName = e.getPath().isEmpty() ? "request" : e.getPath().get(0).getFieldName();

        if (e.getTargetType().equals(LocalDate.class)) {
            errors.put(fieldName, "Date must be in format dd/MM/yyyy");
        } else {
            errors.put(fieldName, "Invalid format");
        }

        ResponseHandler response = new ResponseHandler();
        response.setStatus("Failed");
        response.setError("Invalid request format");
        response.setData(errors);

        return ResponseEntity.badRequest().body(response);
    }

    // JSON Parsing
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseHandler> handleHttpMessageNotReadable(
            HttpMessageNotReadableException e) {

                logger.error("JSON parsing error", e);
        Map<String,String> errors = new HashMap<>();
        Throwable cause = e.getMostSpecificCause();

        if (cause instanceof InvalidFormatException ex) {
            String fieldName = ex.getPath().isEmpty() ? "request" : ex.getPath().get(0).getFieldName();
            errors.put(fieldName, "Invalid format");
        } else {
            errors.put("request", cause.getMessage());
        }

        ResponseHandler response = new ResponseHandler();
        response.setStatus("Failed");
        response.setError("Invalid request format");
        response.setData(errors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // Fallback
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseHandler> handleException(Exception e){
        logger.error("Unhandled exception", e);

        ResponseHandler response = new ResponseHandler();
        response.setStatus("Failed");
        response.setError(e.getClass().getSimpleName());
        response.setMessage(e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
