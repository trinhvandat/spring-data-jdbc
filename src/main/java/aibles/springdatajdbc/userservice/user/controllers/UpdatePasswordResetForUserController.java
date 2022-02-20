package aibles.springdatajdbc.userservice.user.controllers;

import aibles.springdatajdbc.userservice.user.dtos.request.UpdatePasswordResetForUserDTO;
import aibles.springdatajdbc.userservice.user.services.IUpdatePasswordResetForUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users/recover/password")
@ResponseStatus(HttpStatus.OK)
public class UpdatePasswordResetForUserController {
    private final IUpdatePasswordResetForUserService iUpdatePasswordResetForUserService;

    @Autowired
    public UpdatePasswordResetForUserController(IUpdatePasswordResetForUserService iUpdatePasswordResetForUserService) {
        this.iUpdatePasswordResetForUserService = iUpdatePasswordResetForUserService;
    }

    @PatchMapping
    public String execute(@RequestBody @Valid UpdatePasswordResetForUserDTO updatePasswordResetForUserDTO, HttpServletRequest httpServletRequest){
        iUpdatePasswordResetForUserService.execute(updatePasswordResetForUserDTO,httpServletRequest);
        return "Reset password successfully";
    }

}
