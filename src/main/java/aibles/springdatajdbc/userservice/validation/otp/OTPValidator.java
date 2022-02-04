package aibles.springdatajdbc.userservice.validation.otp;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OTPValidator implements ConstraintValidator<OTPConstraint, String> {
    @Override
    public void initialize(OTPConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String otp, ConstraintValidatorContext constraintValidatorContext) {
        return (otp.length() == 6) && otp.matches("[0-9]+");
    }
}
