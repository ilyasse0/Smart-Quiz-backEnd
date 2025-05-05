package net.bensitel.smartquiz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import net.bensitel.smartquiz.entity.enums.Role;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class UploaderByDto {


    private String username;
    private String lastName;
    private String firstName;
    private String email;
    private Role role;

}
