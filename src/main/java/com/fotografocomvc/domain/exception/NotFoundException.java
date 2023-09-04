package com.fotografocomvc.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    private static final long serialVersionUID = -4047125885447907412L;

    public NotFoundException(String message) {
        super(String.format("Failed for [%s]: %s", message));
    }
}