package lt.esdc.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Requirement: 400 error when trying to create an already existing object
    @ExceptionHandler(PotionAlreadyExistsException.class)
    public ResponseEntity<String> handlePotionAlreadyExists(PotionAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    // Requirement: 500 error when trying to delete a non-existent object
    @ExceptionHandler(PotionNotFoundException.class)
    public ResponseEntity<String> handlePotionNotFound(PotionNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    // Requirement: 500 error if DB is empty when getting the list
    @ExceptionHandler(EmptyCauldronException.class)
    public ResponseEntity<String> handleEmptyCauldron(EmptyCauldronException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    // Requirement: Handle Validation API errors and return a clear message to the client
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    // Requirement: Handle @Validated path/query parameter errors
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolation(ConstraintViolationException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    // Requirement: Handle unauthorized modification attempts
    @ExceptionHandler(NotYourPotionException.class)
    public ResponseEntity<String> handleNotYourPotion(NotYourPotionException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }
}