package userservice.exception.handler;


import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import userservice.dto.GenericResponse;
import userservice.exception.DuplicateValueExistException;
import userservice.exception.ResourceNotFoundException;
import userservice.exception.UserAlreadyExistException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @NonNull
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpStatus status) {
        Map<String, String> errors = new HashMap<>();
        exception.getBindingResult().getAllErrors()
                .forEach(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                });
        GenericResponse genericResponse = new GenericResponse(false, "Invalid input details", errors, status.value(), LocalDateTime.now());
        log.error("handling MethodArgumentNotValidException...");
        log.error(exception.getMessage());
        return new ResponseEntity<>(genericResponse, status);
    }

    @NonNull
    protected ResponseEntity<GenericResponse> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException exception, @NonNull HttpHeaders headers, @NonNull HttpStatus status, @NonNull WebRequest request) {
        GenericResponse genericResponse = new GenericResponse(false, exception.getMessage(), Collections.emptyMap(), HttpStatus.METHOD_NOT_ALLOWED.value(), LocalDateTime.now());
        log.error("handling HttpRequestMethodNotSupported...");
        log.error(exception.getMessage());
        return new ResponseEntity<>(genericResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<GenericResponse> handleResourceNotFoundException(ResourceNotFoundException exception) {
        GenericResponse genericResponse = new GenericResponse(false, exception.getMessage(), Collections.emptyMap(), HttpStatus.NOT_FOUND.value(), LocalDateTime.now());
        log.error("handling ResourceNotFoundException...");
        log.error(exception.getMessage());
        return new ResponseEntity<>(genericResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(value = DuplicateValueExistException.class)
    public final ResponseEntity<GenericResponse> handleDuplicateValueExistException(DuplicateValueExistException exception) {
        GenericResponse genericResponse = new GenericResponse(false, exception.getMessage(), Collections.emptyMap(), HttpStatus.CONFLICT.value(), LocalDateTime.now());
        log.error("handling DuplicateValueExistException...");
        log.error(exception.getMessage());
        return new ResponseEntity<>(genericResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(value = IllegalStateException.class)
    public final ResponseEntity<GenericResponse> handleIllegalStateException(IllegalStateException exception) {
        GenericResponse genericResponse = new GenericResponse(false, exception.getMessage(), Collections.emptyMap(), HttpStatus.CONFLICT.value(), LocalDateTime.now());
        log.error("handling IllegalStateException...");
        log.error(exception.getMessage());
        return new ResponseEntity<>(genericResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UserAlreadyExistException.class)
    public final ResponseEntity<GenericResponse> handleUserAlreadyExistException(UserAlreadyExistException exception) {
        GenericResponse genericResponse = new GenericResponse(false, exception.getMessage(), Collections.emptyMap(), HttpStatus.CONFLICT.value(), LocalDateTime.now());
        log.error("handling UserAlreadyExistException...");
        log.error(exception.getMessage());
        return new ResponseEntity<>(genericResponse, HttpStatus.CONFLICT);
    }
}
