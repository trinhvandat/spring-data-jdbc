package aibles.springdatajdbc.userservice.user.controllers;

import aibles.springdatajdbc.userservice.user.dtos.request.GetOTPActiveUserReqDTO;
import aibles.springdatajdbc.userservice.user.services.IGetOTPActiveUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users/pre-active")
@ResponseStatus(HttpStatus.NO_CONTENT)
public class GetOTPActiveUserController {

    private final IGetOTPActiveUserService iGetOTPActiveUserService;

    @Autowired
    public GetOTPActiveUserController(IGetOTPActiveUserService iGetOTPActiveUserService) {
        this.iGetOTPActiveUserService = iGetOTPActiveUserService;
    }

    @PostMapping
    public void execute(@RequestBody @Valid GetOTPActiveUserReqDTO getOTPActiveUserReqDTO){
        iGetOTPActiveUserService.execute(getOTPActiveUserReqDTO);
    }
}
