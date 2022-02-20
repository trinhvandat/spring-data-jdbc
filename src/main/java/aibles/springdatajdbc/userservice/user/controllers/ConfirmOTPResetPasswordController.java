package aibles.springdatajdbc.userservice.user.controllers;

import aibles.springdatajdbc.userservice.user.dtos.request.ConfirmOTPResetPasswordDTO;
import aibles.springdatajdbc.userservice.user.dtos.response.ResetPasswordResponseDTO;
import aibles.springdatajdbc.userservice.user.services.IConfirmOTPResetPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users/recover/identify")
@ResponseStatus(HttpStatus.OK)
public class ConfirmOTPResetPasswordController {
    private final IConfirmOTPResetPasswordService iconfirmOTPResetPasswordService;

    @Autowired
    public ConfirmOTPResetPasswordController(IConfirmOTPResetPasswordService iconfirmOTPResetPasswordService) {
        this.iconfirmOTPResetPasswordService = iconfirmOTPResetPasswordService;
    }

    @PostMapping
    public ResetPasswordResponseDTO execute(@RequestBody @Valid ConfirmOTPResetPasswordDTO confirmOTPResetPasswordDTO){
    return iconfirmOTPResetPasswordService.execute(confirmOTPResetPasswordDTO);
    }
}
