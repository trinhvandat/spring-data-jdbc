package aibles.springdatajdbc.userservice.user.dtos.request;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class UpdatePasswordResetForUserDTO {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "New password is required")
    @Length(min = 6, message = "The number characters of new password must be greater than or equal to 6 characters")
    private String newPassword;

    @NotBlank(message = "New password is required")
    @Length(min = 6, message = "The number characters of new password must be greater than or equal to 6 characters")
    private String newPasswordConfirmation;

    public UpdatePasswordResetForUserDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordConfirmation() {
        return newPasswordConfirmation;
    }

    public void setNewPasswordConfirmation(String newPasswordConfirmation) {
        this.newPasswordConfirmation = newPasswordConfirmation;
    }
}
