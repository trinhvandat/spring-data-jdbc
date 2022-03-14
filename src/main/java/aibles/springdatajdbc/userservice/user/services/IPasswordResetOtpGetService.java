package aibles.springdatajdbc.userservice.user.services;

import aibles.springdatajdbc.userservice.user.dtos.request.ResetPassOTPGetDTO;

public interface IPasswordResetOtpGetService {

    void execute(ResetPassOTPGetDTO resetPassOTPGetDTO);

}
