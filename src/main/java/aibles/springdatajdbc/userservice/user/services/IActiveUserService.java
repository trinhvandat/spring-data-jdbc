package aibles.springdatajdbc.userservice.user.services;

import aibles.springdatajdbc.userservice.user.dtos.request.ActiveUserRequestDTO;

public interface IActiveUserService {

    void execute(ActiveUserRequestDTO activeUserRequestDTO);
}
