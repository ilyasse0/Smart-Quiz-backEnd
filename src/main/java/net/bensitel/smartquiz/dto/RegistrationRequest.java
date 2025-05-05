package net.bensitel.smartquiz.dto;

import lombok.*;
import net.bensitel.smartquiz.entity.Departement;
import net.bensitel.smartquiz.entity.Filiere;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegistrationRequest {

    private String username;
    //private String codeApoge;
    private String lastName;
    private String firstName;
    private String email;
    private String password;
    //private Departement departement;
    //private Filiere filiere;

}
