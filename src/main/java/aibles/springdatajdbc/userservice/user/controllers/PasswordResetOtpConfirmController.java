package aibles.springdatajdbc.userservice.user.controllers;

import aibles.springdatajdbc.userservice.user.dtos.request.ConfirmOTPResetPasswordDTO;
import aibles.springdatajdbc.userservice.user.dtos.response.ResetPasswordResponseDTO;
import aibles.springdatajdbc.userservice.user.services.IPasswordResetOtpConfirmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users/recover/identify")
@ResponseStatus(HttpStatus.OK)
public class PasswordResetOtpConfirmController {

    private final IPasswordResetOtpConfirmService iconfirmOTPResetPasswordResetOtpConfirmService;

    @Autowired
    public PasswordResetOtpConfirmController(IPasswordResetOtpConfirmService iconfirmOTPResetPasswordResetOtpConfirmService) {
        this.iconfirmOTPResetPasswordResetOtpConfirmService = iconfirmOTPResetPasswordResetOtpConfirmService;
    }

    @PostMapping
    public ResetPasswordResponseDTO execute(@RequestBody @Valid ConfirmOTPResetPasswordDTO confirmOTPResetPasswordDTO){
        return iconfirmOTPResetPasswordResetOtpConfirmService.execute(confirmOTPResetPasswordDTO);
    }

}
