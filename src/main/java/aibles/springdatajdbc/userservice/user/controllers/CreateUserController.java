package aibles.springdatajdbc.userservice.user.controllers;

import aibles.springdatajdbc.userservice.user.dtos.request.UserRequestDTO;
import aibles.springdatajdbc.userservice.user.dtos.response.UserResponseDTO;
import aibles.springdatajdbc.userservice.user.services.ICreateUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
public class CreateUserController {

    private final ICreateUserService iCreateUserService;

    @Autowired
    public CreateUserController(ICreateUserService iCreateUserService) {
        this.iCreateUserService = iCreateUserService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponseDTO execute(@RequestBody @Valid UserRequestDTO userRequestDTO){
        return iCreateUserService.execute(userRequestDTO);
    }

}
