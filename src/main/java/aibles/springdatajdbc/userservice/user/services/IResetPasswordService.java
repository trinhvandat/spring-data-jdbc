package aibles.springdatajdbc.userservice.user.services;

import aibles.springdatajdbc.userservice.user.dtos.request.ResetPasswordDTO;

import java.util.concurrent.ExecutionException;

public interface IResetPasswordService {
    void sendOTPForEmailRequest(ResetPasswordDTO resetPasswordDTO);
    void savePassword(ResetPasswordDTO resetPasswordDTO, String otpCheck) throws ExecutionException;
}
