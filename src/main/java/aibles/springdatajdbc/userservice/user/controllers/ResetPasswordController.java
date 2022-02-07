package aibles.springdatajdbc.userservice.user.controllers;

import aibles.springdatajdbc.userservice.user.dtos.request.ResetPasswordDTO;
import aibles.springdatajdbc.userservice.user.services.IResetPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/v1/users/reset-password")
public class ResetPasswordController {
    private final IResetPasswordService iResetPasswordService;

    @Autowired
    public ResetPasswordController(IResetPasswordService iResetPasswordService) {
        this.iResetPasswordService = iResetPasswordService;
    }

    @PostMapping("/send-otp")
    public ResponseEntity<Object> sendOTP(@RequestBody @Valid ResetPasswordDTO resetPasswordDTO){
        iResetPasswordService.sendOTPForEmailRequest(resetPasswordDTO);
        return ResponseEntity.ok("OTP Sent Successfully");
    }
    @PostMapping("/save-password")
    public ResponseEntity<Object> savePassword(@RequestBody @Valid ResetPasswordDTO resetPasswordDTO, @RequestBody String otpCheck) throws ExecutionException {
        iResetPasswordService.savePassword(resetPasswordDTO,otpCheck);
        return ResponseEntity.ok("Password Reset Successful");
    }

}
