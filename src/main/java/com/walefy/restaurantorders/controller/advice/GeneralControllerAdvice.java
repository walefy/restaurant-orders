package com.walefy.restaurantorders.controller.advice;


import com.walefy.restaurantorders.exception.NotFoundException;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GeneralControllerAdvice {
  @ExceptionHandler({ AccessDeniedException.class })
  public ResponseEntity<Map<String, String>> handleAccessDenied(AccessDeniedException e) {
    Map<String, String> response = Map.of("message", e.getMessage());

    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
  }

  @ExceptionHandler({ NotFoundException.class })
  public ResponseEntity<Map<String, String>> handleNotFound(NotFoundException e) {
    Map<String, String> response = Map.of("message", e.getMessage());

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }

  @ExceptionHandler({ Exception.class })
  public ResponseEntity<Map<String, String>> handleGeneric(Exception e) {
    Map<String, String> response = Map.of("message", e.getMessage());

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }
}
