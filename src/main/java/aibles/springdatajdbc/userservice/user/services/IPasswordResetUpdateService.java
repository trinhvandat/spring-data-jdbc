package aibles.springdatajdbc.userservice.user.services;

import aibles.springdatajdbc.userservice.user.dtos.request.UpdatePasswordResetForUserDTO;

import javax.servlet.http.HttpServletRequest;

public interface IPasswordResetUpdateService {

    void execute(UpdatePasswordResetForUserDTO updatePasswordResetForUserDTO,  String token);

}
