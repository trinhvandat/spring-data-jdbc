package aibles.springdatajdbc.userservice.user.services.imls;

import aibles.springdatajdbc.userservice.authentication.components.IJwtService;
import aibles.springdatajdbc.userservice.exceptions.BadRequestException;
import aibles.springdatajdbc.userservice.exceptions.ServerIntervalException;
import aibles.springdatajdbc.userservice.user.dtos.request.ConfirmOTPResetPasswordDTO;
import aibles.springdatajdbc.userservice.user.dtos.response.ResetPasswordResponseDTO;
import aibles.springdatajdbc.userservice.user.repositories.IUserInfoRepository;
import aibles.springdatajdbc.userservice.user.services.IPasswordResetOtpConfirmService;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

@Service
public class PasswordResetOtpConfirmServiceIml implements IPasswordResetOtpConfirmService {

    private static final Logger LOG = Logger.getLogger(ConfirmOTPResetPasswordDTO.class.getName());

    private final LoadingCache<String, String> cache;
    private final IJwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final IUserInfoRepository userInfoRepository;

    @Autowired
    public PasswordResetOtpConfirmServiceIml(final LoadingCache<String, String> cache,
                                             final IJwtService jwtService,
                                             final UserDetailsService userDetailsService,
                                             final IUserInfoRepository userInfoRepository) {
        this.cache = cache;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.userInfoRepository = userInfoRepository;
    }


    @Override
    public ResetPasswordResponseDTO execute(ConfirmOTPResetPasswordDTO confirmOTPResetPasswordDTO) {
        validateOTP(confirmOTPResetPasswordDTO);
        String token=generateTokenResetPassword();
        cache.put("token"+confirmOTPResetPasswordDTO.getEmail(),token);
        return new ResetPasswordResponseDTO(token);
    }

    private String generateTokenResetPassword() {
        String token = UUID.randomUUID().toString();
        return token;
    }

    private void validateOTP(ConfirmOTPResetPasswordDTO confirmOTPResetPasswordDTO) {
        Map<String, String> errorMap = new HashMap<>();
        try {
            Optional.ofNullable(cache.get(confirmOTPResetPasswordDTO.getEmail()))
                    .ifPresentOrElse(
                            otp -> {
                                if (!confirmOTPResetPasswordDTO.getOtp().equals(otp)) {
                                    errorMap.put("OTP", "Invalid OTP.");
                                }
                            },
                            () -> errorMap.put("OTP", "OTP expired.")
                    );
        } catch (ExecutionException e) {
            LOG.warning("Can not get otp from cache with key: " + confirmOTPResetPasswordDTO.getEmail());
            throw new ServerIntervalException("Internal server error");
        }
        if (!errorMap.isEmpty()) {
            throw new BadRequestException(errorMap);
        }

    }

}
