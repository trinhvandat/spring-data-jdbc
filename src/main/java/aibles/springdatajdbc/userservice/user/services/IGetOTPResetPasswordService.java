package aibles.springdatajdbc.userservice.user.services;

import aibles.springdatajdbc.userservice.user.dtos.request.GetOTPResetPasswordDTO;

public interface IGetOTPResetPasswordService {
    void execute(GetOTPResetPasswordDTO getOTPResetPasswordDTO);
}
