package aibles.springdatajdbc.userservice.exceptions.handle;

import aibles.springdatajdbc.userservice.exceptions.custom_response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidatorConstraintHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionResponse execute(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setError("Bad request");
        exceptionResponse.setMessage(errors);
        exceptionResponse.setTimestamp(Instant.now());
        return exceptionResponse;
    }
}
