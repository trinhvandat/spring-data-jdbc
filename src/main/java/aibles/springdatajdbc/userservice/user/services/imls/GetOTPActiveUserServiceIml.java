package aibles.springdatajdbc.userservice.user.services.imls;

import aibles.springdatajdbc.userservice.exceptions.BadRequestException;
import aibles.springdatajdbc.userservice.mail.dto.req.MailRequestDTO;
import aibles.springdatajdbc.userservice.mail.service.IMailService;
import aibles.springdatajdbc.userservice.user.dtos.request.GetOTPActiveUserReqDTO;
import aibles.springdatajdbc.userservice.user.repositories.IUserInfoRepository;
import aibles.springdatajdbc.userservice.user.services.IGetOTPActiveUserService;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class GetOTPActiveUserServiceIml implements IGetOTPActiveUserService {

    private final IUserInfoRepository iUserInfoRepository;
    private final IMailService iMailService;
    private final LoadingCache<String, String> otpCache;

    @Autowired
    public GetOTPActiveUserServiceIml(IUserInfoRepository iUserInfoRepository, IMailService iMailService, LoadingCache<String, String> otpCache) {
        this.iUserInfoRepository = iUserInfoRepository;
        this.iMailService = iMailService;
        this.otpCache = otpCache;
    }

    @Override
    public void execute(GetOTPActiveUserReqDTO getOTPActiveUserReqDTO) {
        validateGetOTPActiveRequestForm(getOTPActiveUserReqDTO);
        sendOTPActiveUser(getOTPActiveUserReqDTO.getEmail());
    }

    private void validateGetOTPActiveRequestForm(GetOTPActiveUserReqDTO getOTPActiveUserReqDTO){
        Map<String, String> errorMap = new HashMap<>();

        iUserInfoRepository.retrieveUserByEmail(getOTPActiveUserReqDTO.getEmail())
                .ifPresentOrElse(
                        userInfo -> {
                            if (userInfo.isActive()){
                                errorMap.put("user", "User activated");
                            }
                        },
                        () -> {
                            errorMap.put("email", "Email is not registered");
                        }
                );

        if (!errorMap.isEmpty()){
            throw new BadRequestException(errorMap);
        }
    }

    private void sendOTPActiveUser(String email){
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
