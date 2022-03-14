package aibles.springdatajdbc.userservice.user.controllers;

import aibles.springdatajdbc.userservice.user.dtos.request.UpdatePasswordResetForUserDTO;
import aibles.springdatajdbc.userservice.user.services.IPasswordResetUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users/recover/password")
@ResponseStatus(HttpStatus.NO_CONTENT)
public class PasswordResetPatchController {

    private final IPasswordResetUpdateService iPasswordResetUpdateService;

    @Autowired
    public PasswordResetPatchController(IPasswordResetUpdateService updatePasswordResetForUserService) {
        this.iPasswordResetUpdateService = updatePasswordResetForUserService;
    }

    @PatchMapping
    public void execute(@RequestBody @Valid UpdatePasswordResetForUserDTO updatePasswordResetForUserDTO,
                        @RequestParam String token){
        iPasswordResetUpdateService.execute(updatePasswordResetForUserDTO,token);
    }

}
