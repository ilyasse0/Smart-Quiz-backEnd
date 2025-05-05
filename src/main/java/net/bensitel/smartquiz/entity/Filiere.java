package net.bensitel.smartquiz.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

//@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Filiere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    private String nameFiliere;
//    @OneToMany(mappedBy = "filiere", fetch = FetchType.LAZY)
//    private List<User> users;

}
