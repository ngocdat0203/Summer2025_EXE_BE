package com.example.lovenhavestopsystem.core.exception;

import com.example.lovenhavestopsystem.core.base.BaseException;
import org.springframework.http.HttpStatus;

public class BadRequestException extends BaseException {
    public BadRequestException(String message) {
        super("BAD_REQUEST", message, HttpStatus.BAD_REQUEST);
    }
}
