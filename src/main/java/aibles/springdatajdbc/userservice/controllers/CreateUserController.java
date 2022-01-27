package aibles.springdatajdbc.userservice.controllers;

import aibles.springdatajdbc.userservice.dtos.request.UserRequestDTO;
import aibles.springdatajdbc.userservice.dtos.response.UserResponseDTO;
import aibles.springdatajdbc.userservice.services.ICreateUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public UserResponseDTO execute(@RequestBody UserRequestDTO userRequestDTO){
        return iCreateUserService.execute(userRequestDTO);
    }

}
