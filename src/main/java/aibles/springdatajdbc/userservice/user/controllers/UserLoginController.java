package aibles.springdatajdbc.userservice.user.controllers;

import aibles.springdatajdbc.userservice.user.dtos.request.LoginRequestDTO;
import aibles.springdatajdbc.userservice.user.dtos.response.LoginResponseDTO;
import aibles.springdatajdbc.userservice.user.services.IUserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users/login")
@ResponseStatus(HttpStatus.OK)
public class UserLoginController {

    private final IUserLoginService iUserLoginService;

    @Autowired
    public UserLoginController(IUserLoginService iUserLoginService) {
        this.iUserLoginService = iUserLoginService;
    }

    @PostMapping
    public LoginResponseDTO execute(@RequestBody @Valid LoginRequestDTO loginRequestDTO){
        return iUserLoginService.execute(loginRequestDTO);
    }
}
