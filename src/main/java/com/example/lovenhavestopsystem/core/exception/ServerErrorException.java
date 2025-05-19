package com.example.lovenhavestopsystem.core.exception;

import com.example.lovenhavestopsystem.core.base.BaseException;
import org.springframework.http.HttpStatus;

public class ServerErrorException extends BaseException {
  public ServerErrorException(String message) {
    super("SERVER_ERROR", message, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}

