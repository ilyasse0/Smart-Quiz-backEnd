package net.bensitel.smartquiz.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jdk.jfr.Timestamp;
import lombok.*;
import net.bensitel.smartquiz.entity.enums.Role;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Student")
public class User  implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username is mandatory")
    @Size(min = 3, max = 20, message = "Username must be 3-20 characters long")
    @Column(unique = true, nullable = false)
    private String username;

    @NotBlank(message = "Last name is mandatory")
    @Size(max = 50, message = "Last name cannot exceed 50 characters")
    @Column(nullable = false)
    private String lastName;

    @NotBlank(message = "First name is mandatory")
    @Size(max = 50, message = "First name cannot exceed 50 characters")
    @Column(nullable = false)
    private String firstName;


    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email must be valid (e.g., user@example.com)")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    // todo --> i removed this pattern cuz its  driving me crazy in dev mnin nsali anrej3ha
//    @Pattern(
//            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
//            message = "Password must contain at least 1 uppercase, 1 lowercase, 1 digit, and 1 special character"
//    )
    private String password; // todo  Will be hashed in the service layer


    @Enumerated(EnumType.STRING)
    private Role role;

//    // todo -> add the filere of the etudiant
//    // todo -> add the department!
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "departement_id")
//    private Departement departement;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "filiere_id")
//    private Filiere filiere;


    private boolean enabled;
    private boolean accountLocked;
    @CreationTimestamp
    private Date createdAt;
    @CreationTimestamp
    private Date updateAt;

    @UpdateTimestamp
    private Date updatedAt;






    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return this.username;
    }


    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.accountLocked;
    }
    @Override
    public boolean isEnabled() {
        return enabled;
    }


    public String getFullName() {
        return username+" "+lastName;
    }











}
