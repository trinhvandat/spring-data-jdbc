package aibles.springdatajdbc.userservice.user.services.imls;

import aibles.springdatajdbc.userservice.exceptions.BadRequestException;
import aibles.springdatajdbc.userservice.mail.dto.req.MailRequestDTO;
import aibles.springdatajdbc.userservice.mail.service.IMailService;
import aibles.springdatajdbc.userservice.user.dtos.request.GetOTPActiveUserReqDTO;
import aibles.springdatajdbc.userservice.user.repositories.IUserInfoRepository;
import aibles.springdatajdbc.userservice.user.services.IGetOTPActiveUserService;
import aibles.springdatajdbc.userservice.util.otp.OTPGenerator;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GetOTPActiveUserServiceIml implements IGetOTPActiveUserService {

    private final IUserInfoRepository iUserInfoRepository;
    private final IMailService iMailService;
    @Qualifier("otp")
    private final LoadingCache<String, String> otpCache;
    private final OTPGenerator otpGenerator;

    @Autowired
    public GetOTPActiveUserServiceIml(IUserInfoRepository iUserInfoRepository, IMailService iMailService, LoadingCache<String, String> otpCache, OTPGenerator otpGenerator) {
        this.iUserInfoRepository = iUserInfoRepository;
        this.iMailService = iMailService;
        this.otpCache = otpCache;
        this.otpGenerator = otpGenerator;
    }

    @Override
    public void execute(GetOTPActiveUserReqDTO getOTPActiveUserReqDTO) {
        validateGetOTPActiveRequestForm(getOTPActiveUserReqDTO);
        final String otp = otpGenerator.execute();
        sendOTPActiveUser(getOTPActiveUserReqDTO.getEmail(),otp);
        otpCache.put(getOTPActiveUserReqDTO.getEmail(),otp);
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

    private void sendOTPActiveUser(String email,String otp){
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


}
