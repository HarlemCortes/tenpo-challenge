package com.harlem.tenpo.sumservice.exceptions;

import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


@ControllerAdvice
public class CustomExceptionHandler {

    public static final String USER_ALREADY_EXIST = "User with this email already exists.";
    public static final String MAX_REQUEST_ALLOWED = "You have exceeded the maximum number of allowed requests. Please try again later.";

    @ExceptionHandler(MissingPercentageException.class)
    public ResponseEntity<String> handleMissingPercentageException(MissingPercentageException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RequestNotPermitted.class)
    public ResponseEntity<String> handleRequestNotPermitted(RequestNotPermitted ex) {
        return new ResponseEntity<>(MAX_REQUEST_ALLOWED, HttpStatus.TOO_MANY_REQUESTS);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        return new ResponseEntity<>(USER_ALREADY_EXIST, HttpStatus.BAD_REQUEST);
    }

}