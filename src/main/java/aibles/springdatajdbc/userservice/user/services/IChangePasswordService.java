package aibles.springdatajdbc.userservice.user.services;

import aibles.springdatajdbc.userservice.user.dtos.request.ChangePasswordRequestDTO;

public interface IChangePasswordService {
    void execute(ChangePasswordRequestDTO changePasswordRequestDTO);
}
