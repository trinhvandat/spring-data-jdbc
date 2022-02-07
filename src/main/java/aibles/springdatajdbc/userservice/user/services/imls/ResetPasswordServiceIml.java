package aibles.springdatajdbc.userservice.user.services.imls;

import aibles.springdatajdbc.userservice.exceptions.BadRequestException;
import aibles.springdatajdbc.userservice.mail.dto.req.MailRequestDTO;
import aibles.springdatajdbc.userservice.mail.service.IMailService;
import aibles.springdatajdbc.userservice.user.dtos.request.ResetPasswordDTO;
import aibles.springdatajdbc.userservice.user.models.UserInfo;
import aibles.springdatajdbc.userservice.user.repositories.IUserInfoRepository;
import aibles.springdatajdbc.userservice.user.services.IGetOTPActiveUserService;
import aibles.springdatajdbc.userservice.user.services.IResetPasswordService;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

@Service
public class ResetPasswordServiceIml implements IResetPasswordService {

    private final IGetOTPActiveUserService iGetOTPActiveUserService;
    private final IUserInfoRepository iUserInfoRepository;
    private final IMailService iMailService;
    private final LoadingCache<String, String> otpCache;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ResetPasswordServiceIml(IGetOTPActiveUserService iGetOTPActiveUserService, IUserInfoRepository iUserInfoRepository, IMailService iMailService, LoadingCache<String, String> otpCache, PasswordEncoder passwordEncoder) {
        this.iGetOTPActiveUserService = iGetOTPActiveUserService;
        this.iUserInfoRepository = iUserInfoRepository;
        this.iMailService = iMailService;
        this.otpCache = otpCache;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void sendOTPForEmailRequest(ResetPasswordDTO resetPasswordDTO) {

        if (!isExistEmail(resetPasswordDTO.getEmail())) {
            throw new UsernameNotFoundException("User Not Found");
        } else {
            sendOTPActiveUser(resetPasswordDTO.getEmail());
        }
    }

    @Override
    public void savePassword(ResetPasswordDTO resetPasswordDTO, String otpCheck) throws ExecutionException {
        if (validateOTP(resetPasswordDTO, otpCheck)) {
            UserInfo userInfo = iUserInfoRepository.retrieveUserByEmail(resetPasswordDTO.getEmail()).get();
            validateChangePasswordForm(resetPasswordDTO, userInfo);
            userInfo.setPassword(passwordEncoder.encode(resetPasswordDTO.getNewPassword()));
            iUserInfoRepository.save(userInfo);
        }
        else {
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("OTP", "Invalid otp");
            throw new BadRequestException(errorMap);
        }
    }


    private void validateChangePasswordForm(ResetPasswordDTO resetPasswordDTO, UserInfo userInfo) {
        Map<String, String> errorMap = new HashMap<>();
        if (!resetPasswordDTO.getNewPassword().equals(resetPasswordDTO.getNewPasswordConfirmation())) {
            errorMap.put("newPasswordConfirm", "New password confirmed does not match with new password");
        }

        if (!errorMap.isEmpty()) {
            throw new BadRequestException(errorMap);
        }
    }
//if newPassword == oldPassword , should we check it ?


    private boolean validateOTP(ResetPasswordDTO resetPasswordDTO, String otpResult) throws ExecutionException {
        String otpCheck = otpCache.get(resetPasswordDTO.getEmail());
        if (otpCheck == otpResult) {
            return true;
        } else {
            return false;
        }
    }


    private boolean isExistEmail(final String email) {
        return iUserInfoRepository.isExistEmail(email);
    }

    private void sendOTPActiveUser(String email) {
        final String otp = generateRegisterOTP();
        final String message = new StringBuilder()
                .append("Your confirm register account OTP code is ")
                .append(otp)
                .append(". This OTP code will be expired about 3 minutes.").toString();

        MailRequestDTO mailRequestDTO = new MailRequestDTO();
        mailRequestDTO.setReceiver(email);
        mailRequestDTO.setSubject("Confirm register account");
        mailRequestDTO.setMessage(message);

        iMailService.sendMail(mailRequestDTO);

        otpCache.put(email, otp);
    }

    private String generateRegisterOTP() {
        StringBuilder otp = new StringBuilder();
        Random random = new Random();

        int i;
        final int otpLength = 6;

        for (i = 0; i < otpLength; i++) {
            int randomNumber = random.nextInt(9);
            otp.append(randomNumber);
        }

        return otp.toString();
    }
}
