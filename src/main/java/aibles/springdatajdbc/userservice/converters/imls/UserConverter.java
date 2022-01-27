package aibles.springdatajdbc.userservice.converters.imls;

import aibles.springdatajdbc.userservice.converters.IModelConverter;
import aibles.springdatajdbc.userservice.dtos.request.UserRequestDTO;
import aibles.springdatajdbc.userservice.dtos.response.UserResponseDTO;
import aibles.springdatajdbc.userservice.models.UserInfo;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class UserConverter implements IModelConverter<UserInfo, UserRequestDTO, UserResponseDTO> {

    @Override
    public UserResponseDTO convertToDTO(UserInfo userInfo) {
        return new UserResponseDTO.UserResponseDTOBuilder()
                .id(userInfo.getId())
                .username(userInfo.getUsername())
                .email(userInfo.getEmail())
                .password(userInfo.getPassword())
                .build();
    }

    @Override
    public UserInfo convertToEntity(UserRequestDTO userRequestDTO) {
        return new UserInfo.UserBuilder()
                .email(userRequestDTO.getEmail())
                .username(userRequestDTO.getUsername())
                .password(userRequestDTO.getPassword())
                .build();
    }
}
