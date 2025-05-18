package net.bensitel.smartquiz.controller;

import lombok.RequiredArgsConstructor;
import net.bensitel.smartquiz.dto.AttemptDto;
import net.bensitel.smartquiz.dto.AttemptRequest;
import net.bensitel.smartquiz.entity.Attempt;
import net.bensitel.smartquiz.entity.User;
import net.bensitel.smartquiz.repository.AttemptRepository;
import net.bensitel.smartquiz.service.AttemptService;
import net.bensitel.smartquiz.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/attempts")
public class AttemptController {

    private final AttemptRepository attemptRepository;
    private final AttemptService attemptService; // todo -> refactor hna to optimize the controller!!
    private final UserService userService;


    @GetMapping
    public List<AttemptDto> getUserAttempts(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUserName(userDetails.getUsername());
        List<Attempt> attempts = attemptRepository.findByUserOrderByCompletedAtDesc(user);

        return attempts.stream().map(AttemptDto::fromEntity).collect(Collectors.toList());
    }


}
