package net.bensitel.smartquiz.service;

import lombok.RequiredArgsConstructor;
import net.bensitel.smartquiz.entity.User;
import net.bensitel.smartquiz.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public User findByUserName(String userName){
        // todo add later more filtering on the user
        return userRepository.findByUsername(userName).orElseThrow(() -> new RuntimeException("user not found"));
    }

}
