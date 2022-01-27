package aibles.springdatajdbc.userservice.exceptions.custom_response;

import java.time.Instant;

public class ExceptionResponse {
    private Instant timestamp;
    private String error;
    private Object message;

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getError() {
        return error;
    }

    public Object getMessage() {
        return message;
    }
}
