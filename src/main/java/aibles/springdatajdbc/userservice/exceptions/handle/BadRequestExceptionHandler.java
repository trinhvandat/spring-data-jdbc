package aibles.springdatajdbc.userservice.exceptions.handle;

import aibles.springdatajdbc.userservice.exceptions.BadRequestException;
import aibles.springdatajdbc.userservice.exceptions.custom_response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestExceptionHandler {

    @ExceptionHandler(value = {BadRequestException.class})
    public ExceptionResponse execute(BadRequestException error){
        System.out.println(error.getErrorMap());
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setError("Bad request");
        exceptionResponse.setMessage(error.getErrorMap());
        exceptionResponse.setTimestamp(Instant.now());
        return exceptionResponse;
    }
}
