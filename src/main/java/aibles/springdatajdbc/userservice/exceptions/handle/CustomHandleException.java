package aibles.springdatajdbc.userservice.exceptions.handle;

import aibles.springdatajdbc.userservice.exceptions.InvalidCreateUserInputException;
import aibles.springdatajdbc.userservice.exceptions.custom_response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class CustomHandleException {

    @ExceptionHandler(value = {InvalidCreateUserInputException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse handleInvalidUserInputException(InvalidCreateUserInputException error){
        System.out.println("handler error");
        System.out.println(error.getErrorMap());
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setError("Bad request");
        exceptionResponse.setMessage(error.getErrorMap());
        exceptionResponse.setTimestamp(Instant.now());
        return exceptionResponse;
    }
}
