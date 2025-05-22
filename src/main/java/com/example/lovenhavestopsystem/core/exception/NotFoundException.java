package com.example.lovenhavestopsystem.core.exception;

import com.example.lovenhavestopsystem.core.base.BaseException;
import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseException {
    public  NotFoundException(String message) {
        super("NOT_FOUND", message, HttpStatus.NOT_FOUND);
    }
}

