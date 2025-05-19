package com.example.lovenhavestopsystem.core.base;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
public class BaseException extends RuntimeException {
    private final String errorCode;
    private final HttpStatus status;
    private final Map<String, Object> additionalData;

    public BaseException(String errorCode, String message, HttpStatus status) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
        this.additionalData = null;
    }

    public BaseException(String errorCode, String message, HttpStatus status, Map<String, Object> additionalData) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
        this.additionalData = additionalData;
    }
}
