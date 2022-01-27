package aibles.springdatajdbc.userservice.services;

import aibles.springdatajdbc.userservice.dtos.request.UserRequestDTO;
import aibles.springdatajdbc.userservice.dtos.response.UserResponseDTO;

public interface ICreateUserService {
    UserResponseDTO execute(final UserRequestDTO userRequestDTO);
}
