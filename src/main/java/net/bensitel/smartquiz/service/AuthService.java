package net.bensitel.smartquiz.service;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.bensitel.smartquiz.dto.AuthRequest;
import net.bensitel.smartquiz.dto.AuthResponse;
import net.bensitel.smartquiz.dto.RegistrationRequest;
import net.bensitel.smartquiz.entity.Token;
import net.bensitel.smartquiz.entity.User;
import net.bensitel.smartquiz.entity.enums.Role;
import net.bensitel.smartquiz.repository.TokenRepository;
import net.bensitel.smartquiz.repository.UserRepository;
import net.bensitel.smartquiz.security.JwtService;
import net.bensitel.smartquiz.util.EmailTemplateName;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Value("${mailing.frontend.activation-url}")
    private String activationUrl;

    private final AuthenticationManager authManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final  EmailService emailService;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;





//    public AuthResponse login(AuthRequest request) {
//        UsernamePasswordAuthenticationToken authToken =
//                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
//        authManager.authenticate(authToken);
//
//        UserDetails user = userDetailsService.loadUserByUsername(request.getUsername());
//        String jwt = jwtService.generateToken(user);
//
//        return new AuthResponse(jwt);
//    }

    public void register(RegistrationRequest request) throws MessagingException {
       var user = User.builder()
               .email(request.getEmail())
               //.codeApoge(request.getCodeApoge())
               .role(Role.ETUDIANT)
               .firstName(request.getFirstName())
               .lastName(request.getLastName())
               .accountLocked(false)
               .username(request.getUsername())
               .password(passwordEncoder.encode(request.getPassword()))
               //.departement(request.getDepartement())
               //.filiere(request.getFiliere())
               .enabled(false)
               .build();
        userRepository.save(user);

        // validation email for validate usser email sprint 2
        sendValidationEmail(user);


//        // Generate JWT
//        String token = jwtService.generateToken(user);
//
//        return new AuthResponse(token);
    }

    private void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);
        emailService.sendEmail(
                user.getEmail(),
                user.getFullName(),
                EmailTemplateName.ACTIVATION_ACCOUNT,
                activationUrl,
                newToken,
                "Account activation"
        );
    }

    private String generateAndSaveActivationToken(User user) {
        //first we generate a token
        String generatedToken = generateActivationCode(6);
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        tokenRepository.save(token);
        return generatedToken;

    }

    private String generateActivationCode(int length) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();

        SecureRandom secureRandom = new SecureRandom();

        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }

        return codeBuilder.toString();
    }

    public AuthResponse authenticate(@Valid AuthRequest request) {
        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var claims = new HashMap<String, Object>();
        var springSecurityUser = (org.springframework.security.core.userdetails.User) auth.getPrincipal();
        String username = springSecurityUser.getUsername();
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        claims.put("fullName", user.getFullName());
        var jwtToken = jwtService.generateToken(claims, user);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();

    }

    public void activateAccount(String token) throws MessagingException {
        //todo --> better handle this exception
        Token savedToken = tokenRepository.findByToken(token).orElseThrow(() -> new RuntimeException("Invalid token"));
        if(LocalDateTime.now().isAfter(savedToken.getExpiresAt())){
            sendValidationEmail(savedToken.getUser());
            throw new RuntimeException("Activation token has expired. Please check your Email ");
        }
        var user = userRepository.findById(savedToken.getUser().getId()).orElseThrow(() -> new RuntimeException("User not found"));
        user.setEnabled(true);
        userRepository.save(user);

        savedToken.setValidatedAt(LocalDateTime.now());
        tokenRepository.save(savedToken);
    }

}
