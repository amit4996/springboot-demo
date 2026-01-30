package com.example.demo.exception;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Error> handleProductNotFoundException(ProductNotFoundException exception,
    HttpServletRequest request){
          Error error = new Error(HttpStatus.NOT_FOUND.value(), exception.getMessage(),request.getRequestURI());
          return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}
