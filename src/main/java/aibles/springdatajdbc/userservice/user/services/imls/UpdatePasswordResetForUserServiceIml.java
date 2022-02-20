package aibles.springdatajdbc.userservice.user.services.imls;

import aibles.springdatajdbc.userservice.authentication.components.IGetUsernamePrincipalService;
import aibles.springdatajdbc.userservice.authentication.components.IJwtService;
import aibles.springdatajdbc.userservice.exceptions.BadRequestException;
import aibles.springdatajdbc.userservice.user.dtos.request.UpdatePasswordResetForUserDTO;
import aibles.springdatajdbc.userservice.user.models.UserInfo;
import aibles.springdatajdbc.userservice.user.repositories.IUserInfoRepository;
import aibles.springdatajdbc.userservice.user.services.IUpdatePasswordResetForUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
@Service
public class UpdatePasswordResetForUserServiceIml implements IUpdatePasswordResetForUserService {
    private static final Logger logger = Logger.getLogger(UpdatePasswordResetForUserServiceIml.class.getName());
    private final IUserInfoRepository iUserInfoRepository;
    private final PasswordEncoder passwordEncoder;
    private final IJwtService iJwtService;
    private final UserDetailsService userDetailsService;
    private final IGetUsernamePrincipalService iGetUsernamePrincipalService;
    private static final String TOKEN_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

//    final String requestTokenHeader = request.getHeader("Authorization"); đây là dùng cho api

    @Autowired
    public UpdatePasswordResetForUserServiceIml(IUserInfoRepository iUserInfoRepository, PasswordEncoder passwordEncoder, IJwtService iJwtService, UserDetailsService userDetailsService, IGetUsernamePrincipalService iGetUsernamePrincipalService) {
        this.iUserInfoRepository = iUserInfoRepository;
        this.passwordEncoder = passwordEncoder;
        this.iJwtService = iJwtService;
        this.userDetailsService = userDetailsService;
        this.iGetUsernamePrincipalService = iGetUsernamePrincipalService;
    }


    @Override
    public void execute(UpdatePasswordResetForUserDTO updatePasswordResetForUserDTO, HttpServletRequest httpServletRequest) {
        validateAccessToken(getTokenFromHeader(httpServletRequest), updatePasswordResetForUserDTO.getEmail());
        validateChangePasswordForm(updatePasswordResetForUserDTO);
        updateNewPasswordReset(updatePasswordResetForUserDTO);
    }

    private void validateAccessToken(String accessToken, String email) {
        UserInfo userInfo = iUserInfoRepository.retrieveUsernameByEmail(email);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(userInfo.getUsername());
        Optional.ofNullable(accessToken).ifPresentOrElse(
                token -> {
                    if (!(iJwtService.isValidToken(token, userDetails))) {
                        Map<String, String> errorMap = new HashMap<>();
                        errorMap.put("token", "Invalid access token!");
                        throw new BadRequestException(errorMap);
                    }
                    ;
                }, () -> {
                    Map<String, String> errorMap = new HashMap<>();
                    errorMap.put("token", "Unable to get JWT Token");
                    throw new BadRequestException(errorMap);
                });

    }

    private void validateChangePasswordForm(UpdatePasswordResetForUserDTO updatePasswordResetForUserDTO) {
        Map<String, String> errorMap = new HashMap<>();

        if (!updatePasswordResetForUserDTO.getNewPassword().equals(updatePasswordResetForUserDTO.getNewPasswordConfirmation())) {
            errorMap.put("newPasswordConfirm", "New password confirmed does not match with new password");
        }

        if (!errorMap.isEmpty()) {
            throw new BadRequestException(errorMap);
        }
    }

    private String getTokenFromHeader(HttpServletRequest httpServletRequest) {
        final String requestTokenHeader = httpServletRequest.getHeader(TOKEN_HEADER);

        if (requestTokenHeader != null && requestTokenHeader.startsWith(TOKEN_PREFIX)) {
            return requestTokenHeader.substring(7);
        } else {
            logger.log(Level.WARNING, "Token does not sign with Bearer String.");
            return null;
        }
    }
    private void updateNewPasswordReset(UpdatePasswordResetForUserDTO updatePasswordResetForUserDTO){
        final String username = iGetUsernamePrincipalService.execute();

        UserInfo userInfo = iUserInfoRepository.retrieveUserByUsername(username).get();
        userInfo.setPassword(passwordEncoder.encode(updatePasswordResetForUserDTO.getNewPassword()));
        iUserInfoRepository.save(userInfo);
    }
}
//check token -> validate password-> lưu password