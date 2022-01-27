package aibles.springdatajdbc.userservice.exceptions;

import java.util.Map;

public class InvalidCreateUserInputException extends RuntimeException {

    private static final long serialVersionUID = 1l;

    private final Map<String, String> errorMap;

    public InvalidCreateUserInputException(Map<String, String> errorMap) {
        super();
        this.errorMap = errorMap;
    }

    public Map<String, String> getErrorMap() {
        return errorMap;
    }
}
