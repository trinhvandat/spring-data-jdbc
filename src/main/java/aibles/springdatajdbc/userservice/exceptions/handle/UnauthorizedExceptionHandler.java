package aibles.springdatajdbc.userservice.exceptions.handle;


import aibles.springdatajdbc.userservice.exceptions.UnauthorizedException;
import aibles.springdatajdbc.userservice.exceptions.custom_response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedExceptionHandler {

    @ExceptionHandler(UnauthorizedException.class)
    public ExceptionResponse execute(UnauthorizedException error){
        System.out.println(error.getMessage());
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setError("Unauthorized error");
        exceptionResponse.setMessage(error.getErrorMap());
        exceptionResponse.setTimestamp(Instant.now());
        return exceptionResponse;
    }
}
