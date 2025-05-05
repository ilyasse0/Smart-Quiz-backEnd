package net.bensitel.smartquiz.controller;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import net.bensitel.smartquiz.dto.AuthRequest;
import net.bensitel.smartquiz.dto.AuthResponse;
import net.bensitel.smartquiz.dto.RegistrationRequest;
import net.bensitel.smartquiz.entity.User;

import net.bensitel.smartquiz.service.AuthService;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final  AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        AuthResponse response = authService.authenticate(request);
        return ResponseEntity.ok(response);
    }



    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequest request) throws MessagingException {
        authService.register(request);
        return ResponseEntity.accepted().build();
    }




    @GetMapping("/activate-account")
    public ResponseEntity<Map<String, String>> confirm(@RequestParam String token) {
        try {
            authService.activateAccount(token);
            return ResponseEntity.ok(Collections.singletonMap("message", "Account activated successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("message", "Invalid or Expired token try again!"));
        }
    }




}
