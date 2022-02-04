package aibles.springdatajdbc.userservice.user.services.imls;

import aibles.springdatajdbc.userservice.authentication.components.IGetUsernamePrincipalService;
import aibles.springdatajdbc.userservice.exceptions.BadRequestException;
import aibles.springdatajdbc.userservice.user.dtos.request.ChangePasswordRequestDTO;
import aibles.springdatajdbc.userservice.user.models.UserInfo;
import aibles.springdatajdbc.userservice.user.repositories.IUserInfoRepository;
import aibles.springdatajdbc.userservice.user.services.IChangePasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ChangePasswordServiceIml implements IChangePasswordService {

    private final IUserInfoRepository iUserInfoRepository;
    private final IGetUsernamePrincipalService iGetUsernamePrincipalService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ChangePasswordServiceIml(IUserInfoRepository iUserInfoRepository, IGetUsernamePrincipalService iGetUsernamePrincipalService, PasswordEncoder passwordEncoder) {
        this.iUserInfoRepository = iUserInfoRepository;
        this.iGetUsernamePrincipalService = iGetUsernamePrincipalService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void execute(ChangePasswordRequestDTO changePasswordRequestDTO) {
        final String username = iGetUsernamePrincipalService.execute();

        UserInfo userInfo = iUserInfoRepository.retrieveUserByUsername(username).get();
        validateChangePasswordForm(changePasswordRequestDTO, userInfo);

        userInfo.setPassword(passwordEncoder.encode(changePasswordRequestDTO.getNewPassword()));
        iUserInfoRepository.save(userInfo);
    }

    private void validateChangePasswordForm(ChangePasswordRequestDTO changePasswordRequestDTO, UserInfo userInfo){
        Map<String, String> errorMap = new HashMap<>();

        if (!passwordEncoder.matches(changePasswordRequestDTO.getOldPassword(), userInfo.getPassword())){
            errorMap.put("oldPassword", "Invalid old password");
        }

        if (changePasswordRequestDTO.getOldPassword().equals(changePasswordRequestDTO.getNewPassword())){
            errorMap.put("newPassword", "New password must be different from the old password");
        }

        if (!changePasswordRequestDTO.getNewPassword().equals(changePasswordRequestDTO.getNewPasswordConfirmation())){
            errorMap.put("newPasswordConfirm", "New password confirmed does not match with new password");
        }

        if (!errorMap.isEmpty()){
            throw new BadRequestException(errorMap);
        }
    }
}
