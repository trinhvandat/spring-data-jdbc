package aibles.springdatajdbc.userservice.exceptions;

import java.util.Map;

public class UnauthorizedException extends RuntimeException {
    private static final long serialVersionUID = 231233223l;

    private final Map<String, String> errorMap;

    public UnauthorizedException(Map<String, String> errorMap) {
        super();
        this.errorMap = errorMap;
    }

    public Map<String, String> getErrorMap() {
        return errorMap;
    }
}
