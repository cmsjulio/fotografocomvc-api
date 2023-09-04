package com.fotografocomvc.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;
@ResponseStatus(HttpStatus.CONFLICT)
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = -4047125885447267412L;

    public BusinessException(String message) {
        super(String.format("Failed for [%s]", message));
    }
}
