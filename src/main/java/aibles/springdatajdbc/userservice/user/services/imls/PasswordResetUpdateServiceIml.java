package aibles.springdatajdbc.userservice.user.services.imls;

import aibles.springdatajdbc.userservice.authentication.components.IGetUsernamePrincipalService;
import aibles.springdatajdbc.userservice.authentication.components.IJwtService;
import aibles.springdatajdbc.userservice.exceptions.BadRequestException;
import aibles.springdatajdbc.userservice.user.dtos.request.UpdatePasswordResetForUserDTO;
import aibles.springdatajdbc.userservice.user.models.UserInfo;
import aibles.springdatajdbc.userservice.user.repositories.IUserInfoRepository;
import aibles.springdatajdbc.userservice.user.services.IPasswordResetUpdateService;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

@Service
public class PasswordResetUpdateServiceIml implements IPasswordResetUpdateService {

    private static final Logger logger = Logger.getLogger(PasswordResetUpdateServiceIml.class.getName());

    private final IUserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;
    private final LoadingCache<String, String> tokenCache;

    @Autowired
    public PasswordResetUpdateServiceIml(IUserInfoRepository userInfoRepository,
                                         PasswordEncoder passwordEncoder,
                                         IJwtService jwtService,
                                         UserDetailsService userDetailsService,
                                         IGetUsernamePrincipalService getUsernamePrincipalService,
                                         LoadingCache<String,String> tokenCache) {
        this.userInfoRepository = userInfoRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.tokenCache = tokenCache;
    }

    @Override
    public void execute(UpdatePasswordResetForUserDTO updatePasswordResetForUserDTO, String token) {
        validateInput(updatePasswordResetForUserDTO);
        validateToken(token, updatePasswordResetForUserDTO.getEmail());
        UserInfo userInfo = userInfoRepository.retrieveUsernameByEmail(updatePasswordResetForUserDTO.getEmail()).get();
        userInfo.setPassword(passwordEncoder.encode(updatePasswordResetForUserDTO.getNewPassword()));
        userInfoRepository.save(userInfo);
    }

    private void validateToken(String token, String email) {
        try {
            String tokenCheck = tokenCache.get("token" + email);
            Optional.ofNullable(token).ifPresentOrElse(
                    check -> {
                        if (!tokenCheck.equals(check)) {
                            Map<String, String> errorMap = new HashMap<>();
                            errorMap.put("token", "Invalid token!");
                            throw new BadRequestException(errorMap);
                        }
                        ;
                    }, () -> {
                        Map<String, String> errorMap = new HashMap<>();
                        errorMap.put("token", "Unable to get Token");
                        throw new BadRequestException(errorMap);
                    });
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    private void validateInput(UpdatePasswordResetForUserDTO updatePasswordResetForUserDTO) {
        Map<String, String> errorMap = new HashMap<>();

        if (!updatePasswordResetForUserDTO.getNewPassword().equals(updatePasswordResetForUserDTO.getNewPasswordConfirmation())) {
            errorMap.put("newPasswordConfirm", "New password confirmed does not match with new password");
        }

        if (!errorMap.isEmpty()) {
            throw new BadRequestException(errorMap);
        }

    }

}
//check token -> validate password-> lưu password