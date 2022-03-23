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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

@Service
public class PasswordResetOtpConfirmServiceIml implements IPasswordResetOtpConfirmService {

    private static final Logger LOG = Logger.getLogger(ConfirmOTPResetPasswordDTO.class.getName());
    @Qualifier("otp")
    private final LoadingCache<String, String> otpCache;
    @Qualifier("token")
    private final LoadingCache<String, String> tokenCache;

    private final IJwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final IUserInfoRepository userInfoRepository;

    @Autowired
    public PasswordResetOtpConfirmServiceIml(final LoadingCache<String, String> otpCache,
                                             final LoadingCache<String, String> tokenCache,
                                             final IJwtService jwtService,
                                             final UserDetailsService userDetailsService,
                                             final IUserInfoRepository userInfoRepository) {
        this.tokenCache = tokenCache;
        this.otpCache=otpCache;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.userInfoRepository = userInfoRepository;
    }


    @Override
    public ResetPasswordResponseDTO execute(ConfirmOTPResetPasswordDTO confirmOTPResetPasswordDTO) {
        validateOTP(confirmOTPResetPasswordDTO);
        String uuid=generateUUID();
        String token=convertHashToString(uuid+confirmOTPResetPasswordDTO.getEmail());
        tokenCache.put("token"+confirmOTPResetPasswordDTO.getEmail(),token);
        return new ResetPasswordResponseDTO(token);
    }

    private String generateUUID() {
        String token = UUID.randomUUID().toString();
        return token;
    }

    private String convertHashToString(String text)  {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");

        byte[] hashInBytes = md.digest(text.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void validateOTP(ConfirmOTPResetPasswordDTO confirmOTPResetPasswordDTO) {
        Map<String, String> errorMap = new HashMap<>();
        try {
            Optional.ofNullable(otpCache.get(confirmOTPResetPasswordDTO.getEmail()))
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
