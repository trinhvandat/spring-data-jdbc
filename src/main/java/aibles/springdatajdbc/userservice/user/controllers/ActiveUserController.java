package aibles.springdatajdbc.userservice.user.controllers;

import aibles.springdatajdbc.userservice.user.dtos.request.ActiveUserRequestDTO;
import aibles.springdatajdbc.userservice.user.services.IActiveUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users/active")
@ResponseStatus(HttpStatus.NO_CONTENT)
public class ActiveUserController {

    private final IActiveUserService iActiveUserService;

    @Autowired
    public ActiveUserController(IActiveUserService iActiveUserService) {
        this.iActiveUserService = iActiveUserService;
    }

    @PostMapping
    public void execute(@RequestBody ActiveUserRequestDTO activeUserRequest){
        iActiveUserService.execute(activeUserRequest);
    }
}
