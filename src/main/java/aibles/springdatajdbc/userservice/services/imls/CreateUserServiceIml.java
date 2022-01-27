package aibles.springdatajdbc.userservice.services.imls;

import aibles.springdatajdbc.userservice.converters.IModelConverter;
import aibles.springdatajdbc.userservice.dtos.request.UserRequestDTO;
import aibles.springdatajdbc.userservice.dtos.response.UserResponseDTO;
import aibles.springdatajdbc.userservice.exceptions.InvalidCreateUserInputException;
import aibles.springdatajdbc.userservice.models.UserInfo;
import aibles.springdatajdbc.userservice.repositories.IUserInfoRepository;
import aibles.springdatajdbc.userservice.services.ICreateUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CreateUserServiceIml implements ICreateUserService {

    private final IUserInfoRepository iUserInfoRepository;
    private final IModelConverter<UserInfo, UserRequestDTO, UserResponseDTO> userConverter;

    @Autowired
    public CreateUserServiceIml(IUserInfoRepository iUserInfoRepository,
                                IModelConverter<UserInfo, UserRequestDTO, UserResponseDTO> userConverter) {
        this.iUserInfoRepository = iUserInfoRepository;
        this.userConverter = userConverter;
    }

    @Override
    public UserResponseDTO execute(UserRequestDTO userRequestDTO) {
        validateUserRequest(userRequestDTO);
        final UserInfo userInfo = iUserInfoRepository.save(userConverter.convertToEntity(userRequestDTO));
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
            throw new InvalidCreateUserInputException(errorMap);
        }
    }

    private boolean isExistUsername(final String username){
        return iUserInfoRepository.isExistUsername(username);
    }

    private boolean isExistEmail(final String email){
        return iUserInfoRepository.isExistEmail(email);
    }
}
