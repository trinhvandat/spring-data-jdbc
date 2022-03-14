package aibles.springdatajdbc.userservice.user.services;

import aibles.springdatajdbc.userservice.user.dtos.request.ConfirmOTPResetPasswordDTO;
import aibles.springdatajdbc.userservice.user.dtos.response.ResetPasswordResponseDTO;

public interface IPasswordResetOtpConfirmService {

  ResetPasswordResponseDTO execute(ConfirmOTPResetPasswordDTO confirmOTPResetPasswordDTO);

}

