package aibles.springdatajdbc.userservice.user.services.imls;

import aibles.springdatajdbc.userservice.exceptions.UserNotFoundException;
import aibles.springdatajdbc.userservice.mail.dto.req.MailRequestDTO;
import aibles.springdatajdbc.userservice.mail.service.IMailService;
import aibles.springdatajdbc.userservice.user.dtos.request.ResetPassOTPGetDTO;
import aibles.springdatajdbc.userservice.user.repositories.IUserInfoRepository;
import aibles.springdatajdbc.userservice.user.services.IPasswordResetOtpGetService;
import aibles.springdatajdbc.userservice.util.otp.OTPGenerator;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


@Service
public class PasswordResetOtpGetServiceIml implements IPasswordResetOtpGetService {

    private final IUserInfoRepository userInfoRepository;
    private final IMailService mailService;
    @Qualifier("otp")
    private final LoadingCache<String, String> otpCache;
    private final OTPGenerator otpGenerator;

    @Autowired
    public PasswordResetOtpGetServiceIml(IUserInfoRepository userInfoRepository,
                                         IMailService mailService,
                                         LoadingCache<String, String> otpCache,
                                         OTPGenerator otpGenerator) {
        this.userInfoRepository = userInfoRepository;
        this.mailService = mailService;
        this.otpCache = otpCache;
        this.otpGenerator = otpGenerator;
    }

    @Override
    public void execute(ResetPassOTPGetDTO resetPassOTPGetDTO) {

        if (isExistEmail(resetPassOTPGetDTO.getEmail())) {
            final String otp = otpGenerator.execute();
            sendOTPResetPassword(resetPassOTPGetDTO.getEmail(),otp);
            otpCache.put(resetPassOTPGetDTO.getEmail(),otp);
        } else {
            throw new UserNotFoundException();
        }
    }

    private boolean isExistEmail(String email) {
        return userInfoRepository.isExistEmail(email);
    }


    private void sendOTPResetPassword(String email,String otp) {
        final String message = new StringBuilder()
                .append("Your confirm reset password OTP code is ")
                .append(otp)
                .append(". This OTP code will be expired about 3 minutes.").toString();

        MailRequestDTO mailRequestDTO = new MailRequestDTO();
        mailRequestDTO.setReceiver(email);
        mailRequestDTO.setSubject("Confirm reset password");
        mailRequestDTO.setMessage(message);
        mailService.sendMail(mailRequestDTO);

    }

}
