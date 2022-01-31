package aibles.springdatajdbc.userservice.user.services;

import aibles.springdatajdbc.userservice.user.dtos.request.UserRequestDTO;
import aibles.springdatajdbc.userservice.user.dtos.response.UserResponseDTO;

public interface ICreateUserService {
    UserResponseDTO execute(final UserRequestDTO userRequestDTO);
}
