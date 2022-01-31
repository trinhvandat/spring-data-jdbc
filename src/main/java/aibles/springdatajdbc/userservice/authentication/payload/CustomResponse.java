package aibles.springdatajdbc.userservice.authentication.payload;

import java.time.Instant;

public class CustomResponse {

    private Instant timestamp;
    private Object message;

    public CustomResponse(Object message) {
        this.timestamp = Instant.now();
        this.message = message;
    }
}
