package com.javatechie.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;


@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity handleUserNotFoundException(AuthenticationException exception) {
        System.out.println("username not found");
        String message = exception.getMessage();
        ApiError error = new ApiError(message);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDenied(AccessDeniedException exception) {
        String message = exception.getMessage();
        ApiError error = new ApiError(message);
        return new ResponseEntity<ApiError>(error, HttpStatus.BAD_REQUEST);

    }
//    @ExceptionHandler(MalformedJwtException.class)
//    public ResponseEntity<ApiError> handleResourceNotFoundException(MalformedJwtException ex) {
//        String message = ex.getMessage();
//        ApiError error = new ApiError(message);
//        return new ResponseEntity<ApiError>(error, HttpStatus.UNAUTHORIZED);
//
//    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ApiError> handleJwtTokenExpiredException(ExpiredJwtException exception){
        String message = exception.getMessage();
        ApiError error = new ApiError(message);
        return new ResponseEntity<ApiError>(error,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGlobalException(Exception e){
        String message = e.getMessage();
        ApiError error = new ApiError(message);
        return new ResponseEntity<ApiError>(error,HttpStatus.BAD_REQUEST);
    }

//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getAllErrors().forEach((error) ->{
//
//            String fieldName = ((FieldError) error).getField();
//            String message = error.getDefaultMessage();
//            errors.put(fieldName, message);
//        });
//        return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
//    }
//    }

}