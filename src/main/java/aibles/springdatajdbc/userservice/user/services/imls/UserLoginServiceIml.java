package aibles.springdatajdbc.userservice.user.services.imls;

import aibles.springdatajdbc.userservice.authentication.components.IJwtService;
import aibles.springdatajdbc.userservice.exceptions.UnauthorizedException;
import aibles.springdatajdbc.userservice.user.dtos.request.LoginRequestDTO;
import aibles.springdatajdbc.userservice.user.dtos.response.LoginResponseDTO;
import aibles.springdatajdbc.userservice.user.repositories.IUserInfoRepository;
import aibles.springdatajdbc.userservice.user.services.IUserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserLoginServiceIml implements IUserLoginService {

    private final IJwtService iJwtService;
    private final UserDetailsService userDetailsService;
    private final IUserInfoRepository iUserInfoRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserLoginServiceIml(final IJwtService iJwtService,
                               final UserDetailsService userDetailsService,
                               final IUserInfoRepository iUserInfoRepository,
                               final PasswordEncoder passwordEncoder) {
        this.iJwtService = iJwtService;
        this.userDetailsService = userDetailsService;
        this.iUserInfoRepository = iUserInfoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public LoginResponseDTO execute(LoginRequestDTO loginRequestDTO) {
        validateLoginRequest(loginRequestDTO);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequestDTO.getUsername());
        final String accessToken = iJwtService.generateJwtToken(userDetails);
        return new LoginResponseDTO(accessToken);
    }

    private void validateLoginRequest(LoginRequestDTO loginRequestDTO){
        Map<String, String> errorMap = new HashMap<>();

        iUserInfoRepository.retrieveUserByUsername(loginRequestDTO.getUsername())
                .ifPresentOrElse(
                        userInfo -> {
                            if (!userInfo.isActive()){
                                errorMap.put("user", "user is not activated");
                            }

                            if (!passwordEncoder.matches(loginRequestDTO.getPassword(), userInfo.getPassword())){
                                errorMap.put("user", "invalid username or password");
                            }
                        },
                        () -> errorMap.put("username", "username does not register")
                );

        if (!errorMap.isEmpty()){
            throw new UnauthorizedException(errorMap);
        }
    }
}
