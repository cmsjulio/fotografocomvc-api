package com.fotografocomvc.api.http.advices;

import com.fotografocomvc.domain.exception.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(RuntimeException ex, WebRequest request) {
        NotFoundException exception = (NotFoundException) ex;
        Map<String, Object> responseBody = exception.getError();

        if (responseBody == null) {
            responseBody = new HashMap<>();
            responseBody.put("errors", Arrays.asList(exception.getMessage()));
        }

        responseBody.put("code", HttpStatus.NOT_FOUND.value());
        responseBody.put("message", exception.getMessage());
        return handleExceptionInternal(ex, responseBody, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, Object> responseBody = new HashMap<>();
        ex.getFieldErrors().forEach(fieldError -> responseBody.put(fieldError.getField(), fieldError.getDefaultMessage()));


        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

}