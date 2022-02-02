package aibles.springdatajdbc.userservice.exceptions;

import java.util.Map;

public class BadRequestException extends RuntimeException {
    private static final long serialVersionUID = 23123l;

    private final Map<String, String> errorMap;

    public BadRequestException(Map<String, String> errorMap) {
        super();
        this.errorMap = errorMap;
    }

    public Map<String, String> getErrorMap() {
        return errorMap;
    }
}
