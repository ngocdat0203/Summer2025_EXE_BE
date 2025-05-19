package com.example.lovenhavestopsystem.core.exception;

import com.example.lovenhavestopsystem.core.base.BaseException;
import org.springframework.http.HttpStatus;

public class ForbiddenException extends BaseException {
  public ForbiddenException(String message) {
    super("FORBIDDEN", message, HttpStatus.FORBIDDEN);
  }
}
