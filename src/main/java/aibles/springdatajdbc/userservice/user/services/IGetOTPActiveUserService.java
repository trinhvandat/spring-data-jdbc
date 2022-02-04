package aibles.springdatajdbc.userservice.user.services;

import aibles.springdatajdbc.userservice.user.dtos.request.GetOTPActiveUserReqDTO;

public interface IGetOTPActiveUserService {

    void execute(GetOTPActiveUserReqDTO getOTPActiveUserReqDTO);
}
