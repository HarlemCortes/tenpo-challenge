package com.harlem.tenpo.sumservice.exceptions;

public class MissingPercentageException  extends RuntimeException {
    public MissingPercentageException(String message) {
        super(message);
    }
}