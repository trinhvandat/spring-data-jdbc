package aibles.springdatajdbc.userservice.authentication.controller;

import aibles.springdatajdbc.userservice.authentication.components.IJwtService;
import aibles.springdatajdbc.userservice.authentication.dto.request.LoginRequestDTO;
import aibles.springdatajdbc.userservice.authentication.dto.response.LoginResponseDTO;
import aibles.springdatajdbc.userservice.authentication.payload.CustomUserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

@RestController
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final IJwtService iJwtService;
    private final UserDetailsService userDetailsService;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, IJwtService iJwtService, UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.iJwtService = iJwtService;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO) throws Exception{
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequestDTO.getUsername());
        final String accessToken = iJwtService.generateJwtToken(userDetails);
        return new LoginResponseDTO(accessToken);
    }

    @GetMapping("/greeting")
    @ResponseStatus(HttpStatus.OK)
    public String sayHello(){
        return "Hello Leonard";
    }
}
