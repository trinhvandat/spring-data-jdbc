package aibles.springdatajdbc.userservice.user.controllers;

import aibles.springdatajdbc.userservice.user.dtos.request.ResetPassOTPGetDTO;
import aibles.springdatajdbc.userservice.user.services.IPasswordResetOtpGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users/recover/initiate")
@ResponseStatus(HttpStatus.NO_CONTENT)
public class PasswordResetOtpGetController {

    private final IPasswordResetOtpGetService igetOTPResetPasswordResetOtpGetServiceIml;

    @Autowired
    public PasswordResetOtpGetController(IPasswordResetOtpGetService igetOTPResetPasswordResetOtpGetService) {
        this.igetOTPResetPasswordResetOtpGetServiceIml = igetOTPResetPasswordResetOtpGetService;
    }

    @PostMapping
    public void execute(@RequestBody @Valid ResetPassOTPGetDTO resetPassOTPGetDTO){
        igetOTPResetPasswordResetOtpGetServiceIml.execute(resetPassOTPGetDTO);
    }
}
