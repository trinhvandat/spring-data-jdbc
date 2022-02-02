package aibles.springdatajdbc.userservice.user.services.imls;

import aibles.springdatajdbc.userservice.exceptions.BadRequestException;
import aibles.springdatajdbc.userservice.exceptions.ServerIntervalException;
import aibles.springdatajdbc.userservice.user.dtos.request.ActiveUserRequestDTO;
import aibles.springdatajdbc.userservice.user.models.UserInfo;
import aibles.springdatajdbc.userservice.user.repositories.IUserInfoRepository;
import aibles.springdatajdbc.userservice.user.services.IActiveUserService;
import com.google.common.cache.LoadingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

@Service
public class ActiveUserServiceIml implements IActiveUserService {

    private static final Logger LOG = Logger.getLogger(ActiveUserServiceIml.class.getName());

    private final IUserInfoRepository iUserInfoRepository;
    private final LoadingCache<String, String> otpCache;

    @Autowired
    public ActiveUserServiceIml(IUserInfoRepository iUserInfoRepository, LoadingCache<String, String> otpCache) {
        this.iUserInfoRepository = iUserInfoRepository;
        this.otpCache = otpCache;
    }

    @Override
    public void execute(ActiveUserRequestDTO activeUserRequestDTO) {
        validateActiveUserInput(activeUserRequestDTO);

        UserInfo userInfo = iUserInfoRepository.retrieveUserByEmail(activeUserRequestDTO.getEmail()).get();
        userInfo.setActive(true);

        iUserInfoRepository.save(userInfo);
    }

    private void validateActiveUserInput(ActiveUserRequestDTO activeUserRequestDTO) {
        Map<String, String> errorMap = new HashMap<>();

        iUserInfoRepository.retrieveUserByEmail(activeUserRequestDTO.getEmail())
                .ifPresentOrElse(
                        userInfo -> {
                            if (userInfo.isActive()){
                                errorMap.put("user", "User is activated.");
                            }
                        },
                        () -> errorMap.put("email", "User does not register")
                );

        try {
            Optional.ofNullable(otpCache.get(activeUserRequestDTO.getEmail()))
                    .ifPresentOrElse(
                            otp -> {
                                if (!activeUserRequestDTO.getOtp().equals(otp)){
                                    errorMap.put("OTP", "Invalid OTP.");
                                }
                            },
                            () -> errorMap.put("OTP", "OTP expired.")
                    );
        } catch (ExecutionException e) {
            LOG.warning("Can not get otp from cache with key: " + activeUserRequestDTO.getEmail());
            throw new ServerIntervalException("Internal server error");
        }

        if (!errorMap.isEmpty()){
            throw new BadRequestException(errorMap);
        }
    }
}
