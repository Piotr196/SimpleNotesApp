package com.simplenotesapp.simplenotesapp.mapper;

import com.simplenotesapp.simplenotesapp.dto.UserDto;
import com.simplenotesapp.simplenotesapp.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserDtoMapper {

    public User mapToEntity(final UserDto userDto) {
        return new User(userDto.getId(),
                userDto.getName(),
                userDto.getSurname(),
                userDto.getPassword());
    }

    public UserDto mapToDto(final User user) {
        return new UserDto(user.getId(),
                user.getName(),
                user.getSurname(),
                user.getPassword());
    }
}
