package ru.kata.spring.boot_security.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleEntityNotFound(NoSuchElementException e) {
        return new ResponseEntity<>("Such object not found", HttpStatus.NOT_FOUND);
    }
}
