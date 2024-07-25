package com.walefy.restaurantorders.controller.advice;


import com.walefy.restaurantorders.exception.InvalidAdminTokenException;
import com.walefy.restaurantorders.exception.NotFoundException;
import com.walefy.restaurantorders.exception.UserAlreadyRegistered;
import java.util.List;
import java.util.Map;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GeneralControllerAdvice {
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException e) {
    List<String> stackErrors = e
      .getBindingResult()
      .getAllErrors()
      .stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
      .toList();

    Map<String, Object> response = Map.of(
      "message", "some invalid fields",
      "stack", stackErrors
    );

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler({ AccessDeniedException.class, InvalidAdminTokenException.class })
  public ResponseEntity<Map<String, String>> handleAccessDenied(Exception e) {
    Map<String, String> response = Map.of("message", e.getMessage());

    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
  }

  @ExceptionHandler({ NotFoundException.class })
  public ResponseEntity<Map<String, String>> handleNotFound(NotFoundException e) {
    Map<String, String> response = Map.of("message", e.getMessage());

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }

  @ExceptionHandler({ UserAlreadyRegistered.class })
  public ResponseEntity<Map<String, String>> handleUserAlreadyRegistered(UserAlreadyRegistered e) {
    Map<String, String> response = Map.of("message", e.getMessage());

    return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
  }

  @ExceptionHandler({ Exception.class })
  public ResponseEntity<Map<String, String>> handleGeneric(Exception e) {
    Map<String, String> response = Map.of("message", e.getMessage());

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
  }
}
