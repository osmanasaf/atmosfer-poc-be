package org.codefirst.seed.userservice.service;

import lombok.AllArgsConstructor;
import org.codefirst.seed.userservice.entity.User;
import org.codefirst.seed.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getByUsername(String username) {
        Optional<User> userOpt = userRepository.getUserByUsername(username);
        if(userOpt.isEmpty()) {
            throw new RuntimeException("Not found");
        }
        return userOpt.get();
    }
}
