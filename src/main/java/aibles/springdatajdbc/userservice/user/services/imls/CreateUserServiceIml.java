package aibles.springdatajdbc.userservice.user.services.imls;

import aibles.springdatajdbc.userservice.converters.IModelConverter;
import aibles.springdatajdbc.userservice.exceptions.BadRequestException;
import aibles.springdatajdbc.userservice.mail.dto.req.MailRequestDTO;
import aibles.springdatajdbc.userservice.mail.service.IMailService;
import aibles.springdatajdbc.userservice.user.dtos.request.UserRequestDTO;
import aibles.springdatajdbc.userservice.user.dtos.response.UserResponseDTO;
import aibles.springdatajdbc.userservice.user.models.UserInfo;
import aibles.springdatajdbc.userservice.user.repositories.IUserInfoRepository;
import aibles.springdatajdbc.userservice.user.services.ICreateUserService;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

@Service
public class CreateUserServiceIml implements ICreateUserService {

    private static final Logger LOG = Logger.getLogger(CreateUserServiceIml.class.getName());

    private final IUserInfoRepository iUserInfoRepository;
    private final IModelConverter<UserInfo, UserRequestDTO, UserResponseDTO> userConverter;
    private final PasswordEncoder passwordEncoder;
    private final IMailService iMailService;
    private final LoadingCache<String, String> otpCache;

    @Autowired
    public CreateUserServiceIml(IUserInfoRepository iUserInfoRepository,
                                IModelConverter<UserInfo, UserRequestDTO, UserResponseDTO> userConverter,
                                PasswordEncoder passwordEncoder,
                                IMailService iMailService, LoadingCache<String, String> otpCache) {
        this.iUserInfoRepository = iUserInfoRepository;
        this.userConverter = userConverter;
        this.passwordEncoder = passwordEncoder;
        this.iMailService = iMailService;
        this.otpCache = otpCache;
    }

    @Override
    public UserResponseDTO execute(UserRequestDTO userRequestDTO) {
        validateUserRequest(userRequestDTO);

        UserInfo userInfo = userConverter.convertToEntity(userRequestDTO);
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userInfo.setActive(false);

        userInfo = iUserInfoRepository.save(userInfo);

        sendOTPConfirmRegister(userInfo.getEmail());

        return userConverter.convertToDTO(userInfo);
    }

    private void validateUserRequest(UserRequestDTO userRequestDTO){
        Map<String, String> errorMap = new HashMap<String, String>();

        if (isExistUsername(userRequestDTO.getUsername())){
            errorMap.put("username", "Username existed");
        }

        if (isExistEmail(userRequestDTO.getEmail())){
            errorMap.put("email", "Email existed");
        }

        if (!errorMap.isEmpty()){
            throw new BadRequestException(errorMap);
        }
    }

    private boolean isExistUsername(final String username){
        return iUserInfoRepository.isExistUsername(username);
    }

    private boolean isExistEmail(final String email){
        return iUserInfoRepository.isExistEmail(email);
    }

    private void sendOTPConfirmRegister(final String email){
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
