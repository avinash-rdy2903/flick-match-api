package com.flickmatch.api.handler;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handleException(IllegalStateException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleException(MethodArgumentNotValidException e){
        String errMsg = e.getBindingResult().getFieldErrors()
                .stream().map(err->err.getDefaultMessage())
                .collect(Collectors.joining(","));
        return ResponseEntity.badRequest().body(errMsg);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleException(DataIntegrityViolationException e){
        String errMsg = e.getMostSpecificCause().getMessage();
        return ResponseEntity.badRequest().body(errMsg);
    }
    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<?> handleException(TransactionSystemException e){
        return ResponseEntity.badRequest().body(e.getLocalizedMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e){
        return ResponseEntity.badRequest().body(e.getLocalizedMessage());
    }
}