package aibles.springdatajdbc.userservice.user.dtos.request;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

public class ChangePasswordRequestDTO {

    @NotBlank(message = "Old password is required")
    private String oldPassword;

    @NotBlank(message = "New password is required")
    @Length(min = 6, message = "The number characters of new password must be greater than or equal to 6 characters")
    private String newPassword;

    @NotBlank(message = "New password is required")
    @Length(min = 6, message = "The number characters of new password must be greater than or equal to 6 characters")
    private String newPasswordConfirmation;

    public ChangePasswordRequestDTO() {
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
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
