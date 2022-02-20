package aibles.springdatajdbc.userservice.user.controllers;

import aibles.springdatajdbc.userservice.user.dtos.request.GetOTPResetPasswordDTO;
import aibles.springdatajdbc.userservice.user.services.IGetOTPResetPasswordService;
import aibles.springdatajdbc.userservice.user.services.imls.GetOTPResetPasswordServiceIml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users/recover/initiate")
@ResponseStatus(HttpStatus.NO_CONTENT)
public class GetOTPResetPasswordController {
    private final IGetOTPResetPasswordService igetOTPResetPasswordServiceIml;
    @Autowired
    public GetOTPResetPasswordController(IGetOTPResetPasswordService igetOTPResetPasswordService) {
        this.igetOTPResetPasswordServiceIml = igetOTPResetPasswordService;
    }
    @PostMapping
    public void execute(@RequestBody @Valid GetOTPResetPasswordDTO getOTPResetPasswordDTO){
        igetOTPResetPasswordServiceIml.execute(getOTPResetPasswordDTO);
    }
}
