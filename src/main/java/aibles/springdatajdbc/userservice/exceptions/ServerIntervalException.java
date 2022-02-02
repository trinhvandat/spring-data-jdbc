package aibles.springdatajdbc.userservice.exceptions;

public class ServerIntervalException extends RuntimeException {

    private static final long serialVersionUID = 21341234124231l;

    private final String message;

    public ServerIntervalException(String message) {
        super();
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
