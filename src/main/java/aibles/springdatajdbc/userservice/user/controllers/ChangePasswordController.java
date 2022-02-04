package aibles.springdatajdbc.userservice.user.controllers;

import aibles.springdatajdbc.userservice.user.dtos.request.ChangePasswordRequestDTO;
import aibles.springdatajdbc.userservice.user.services.IChangePasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users/change-password")
@ResponseStatus(HttpStatus.OK)
public class ChangePasswordController {

    private final IChangePasswordService iChangePasswordService;

    @Autowired
    public ChangePasswordController(IChangePasswordService iChangePasswordService) {
        this.iChangePasswordService = iChangePasswordService;
    }

    @PostMapping
    public void execute(@RequestBody @Valid ChangePasswordRequestDTO changePasswordRequestDTO){
        iChangePasswordService.execute(changePasswordRequestDTO);
    }
}
