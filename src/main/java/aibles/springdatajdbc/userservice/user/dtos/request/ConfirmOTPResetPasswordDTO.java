package aibles.springdatajdbc.userservice.user.dtos.request;

import aibles.springdatajdbc.userservice.validation.otp.OTPConstraint;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class ConfirmOTPResetPasswordDTO {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "OTP is required")
    @OTPConstraint
    private String otp;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

}
