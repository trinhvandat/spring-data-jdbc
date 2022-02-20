package aibles.springdatajdbc.userservice.user.services.imls;

import aibles.springdatajdbc.userservice.authentication.components.IJwtService;
import aibles.springdatajdbc.userservice.exceptions.BadRequestException;
import aibles.springdatajdbc.userservice.exceptions.ServerIntervalException;
import aibles.springdatajdbc.userservice.user.dtos.request.ConfirmOTPResetPasswordDTO;
import aibles.springdatajdbc.userservice.user.dtos.response.ResetPasswordResponseDTO;
import aibles.springdatajdbc.userservice.user.models.UserInfo;
import aibles.springdatajdbc.userservice.user.repositories.IUserInfoRepository;
import aibles.springdatajdbc.userservice.user.services.IConfirmOTPResetPasswordService;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

@Service
public class ConfirmOTPResetPasswordServiceIml implements IConfirmOTPResetPasswordService {
    private static final Logger LOG = Logger.getLogger(ConfirmOTPResetPasswordDTO.class.getName());

    private final LoadingCache<String, String> loadingCache;
    private final IJwtService iJwtService;
    private final UserDetailsService userDetailsService;
    private final IUserInfoRepository iUserInfoRepository;

    @Autowired
    public ConfirmOTPResetPasswordServiceIml(final LoadingCache<String, String> loadingCache,
                                             final IJwtService iJwtService,
                                             final UserDetailsService userDetailsService,
                                             final IUserInfoRepository iUserInfoRepository) {
        this.loadingCache = loadingCache;
        this.iJwtService = iJwtService;
        this.userDetailsService = userDetailsService;
        this.iUserInfoRepository = iUserInfoRepository;
    }


    @Override
    public ResetPasswordResponseDTO execute(ConfirmOTPResetPasswordDTO confirmOTPResetPasswordDTO) {
    validateOTP(confirmOTPResetPasswordDTO);
        return getAccessTokenResetPassword(confirmOTPResetPasswordDTO);
    }

    private ResetPasswordResponseDTO getAccessTokenResetPassword(ConfirmOTPResetPasswordDTO confirmOTPResetPasswordDTO) {
        UserInfo userInfo = iUserInfoRepository.retrieveUsernameByEmail(confirmOTPResetPasswordDTO.getEmail());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(userInfo.getUsername());
        final String accessToken = iJwtService.generateJwtToken(userDetails);
        return new ResetPasswordResponseDTO(accessToken);
    }

    private void validateOTP(ConfirmOTPResetPasswordDTO confirmOTPResetPasswordDTO) {
        Map<String, String> errorMap = new HashMap<>();
        try {
            Optional.ofNullable(loadingCache.get(confirmOTPResetPasswordDTO.getEmail()))
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
