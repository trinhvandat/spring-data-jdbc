package aibles.springdatajdbc.userservice.validation.otp;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = OTPValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface OTPConstraint {

    String message() default "Invalid OTP format. OTP has 6 digits.";
    Class<?> [] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
