package aibles.springdatajdbc.userservice.exceptions.handle;

import aibles.springdatajdbc.userservice.exceptions.ServerIntervalException;
import aibles.springdatajdbc.userservice.exceptions.custom_response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class IntervalServerErrorHandler {

    @ExceptionHandler(value = {ServerIntervalException.class})
    public ExceptionResponse execute(ServerIntervalException error){
        System.out.println(error.getMessage());
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setError("Server internal error");
        exceptionResponse.setMessage(error.getMessage());
        exceptionResponse.setTimestamp(Instant.now());
        return exceptionResponse;
    }
}
