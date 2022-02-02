package aibles.springdatajdbc.userservice.user.services;

import aibles.springdatajdbc.userservice.user.dtos.request.LoginRequestDTO;
import aibles.springdatajdbc.userservice.user.dtos.response.LoginResponseDTO;

public interface IUserLoginService {

    LoginResponseDTO execute(LoginRequestDTO loginRequestDTO);
}
