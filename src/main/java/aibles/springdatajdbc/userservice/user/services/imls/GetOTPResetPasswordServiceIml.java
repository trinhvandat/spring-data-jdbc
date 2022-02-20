package aibles.springdatajdbc.userservice.user.services.imls;

import aibles.springdatajdbc.userservice.mail.dto.req.MailRequestDTO;
import aibles.springdatajdbc.userservice.mail.service.IMailService;
import aibles.springdatajdbc.userservice.user.dtos.request.GetOTPResetPasswordDTO;
import aibles.springdatajdbc.userservice.user.repositories.IUserInfoRepository;
import aibles.springdatajdbc.userservice.user.services.IGetOTPResetPasswordService;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;


@Service
public class GetOTPResetPasswordServiceIml implements IGetOTPResetPasswordService {

    private final IUserInfoRepository userInfoRepository;
    private final IMailService iMailService;
    private final LoadingCache<String, String> otpCache;

    @Autowired
    public GetOTPResetPasswordServiceIml(IUserInfoRepository userInfoRepository, IMailService iMailService, LoadingCache<String, String> otpCache) {
        this.userInfoRepository = userInfoRepository;
        this.iMailService = iMailService;
        this.otpCache = otpCache;
    }

    @Override
    public void execute(GetOTPResetPasswordDTO getOTPResetPasswordDTO) {
        Optional.of(isExistEmail(getOTPResetPasswordDTO.getEmail()))
                .ifPresentOrElse(
                        checkMail -> {
                            sendOTPResetPassword(getOTPResetPasswordDTO.getEmail());
                        },
                        () -> {
                            throw new UsernameNotFoundException("");//nhớ handler exception
                        });
    }

    private boolean isExistEmail(String email) {
        boolean checkMail = userInfoRepository.isExistEmail(email);
        return checkMail ? true : false;

    }


    private void sendOTPResetPassword(String email) {
        final String otp = generateRegisterOTP();
        final String message = new StringBuilder()
                .append("Your confirm reset password OTP code is ")
                .append(otp)
                .append(". This OTP code will be expired about 3 minutes.").toString();

        MailRequestDTO mailRequestDTO = new MailRequestDTO();
        mailRequestDTO.setReceiver(email);
        mailRequestDTO.setSubject("Confirm reset password");
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
