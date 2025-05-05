package net.bensitel.smartquiz.mapper;

import net.bensitel.smartquiz.dto.UploaderByDto;
import net.bensitel.smartquiz.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UploaderByDto toDto(User user) {
        if (user == null) return null;

        return UploaderByDto.builder()
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

}
