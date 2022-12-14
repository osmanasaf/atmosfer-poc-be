package org.codefirst.seed.userservice.service;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.codefirst.seed.userservice.entity.User;
import org.codefirst.seed.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @PostConstruct
    private void init() {
        User user = new User();
        user.setUsername("mahmut");
        user.setPassword("1234");
        userRepository.save(user);
    }

    public User getByUsername(String username) {
        Optional<User> userOpt = userRepository.getByUsername(username);
        if(userOpt.isEmpty()) {
            throw new RuntimeException("Not found");
        }
        return userOpt.get();
    }
}
