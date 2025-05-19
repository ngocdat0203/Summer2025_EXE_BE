package com.example.lovenhavestopsystem.core.exception;

import com.example.lovenhavestopsystem.core.base.BaseException;
import org.springframework.http.HttpStatus;

public class UnauthorizedException extends BaseException {
    public UnauthorizedException(String message) {
        super("UNAUTHORIZED", message, HttpStatus.UNAUTHORIZED);
    }
}
