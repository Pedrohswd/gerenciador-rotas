package com.malmitas.backend.service;

import com.malmitas.backend.model.User;
import com.malmitas.backend.model.UserDTO;
import com.malmitas.backend.repository.UserRepository;
import com.malmitas.backend.utils.Utils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(UserDTO user) {
        if (!userRepository.existsByUsername(user.getUsername())) {
            User newUser = new User();
            newUser.setUsername(user.getUsername());
            newUser.setPassword(Utils.hashPassword(user.getPassword()));
            newUser.addRole(user.getRole());
            return userRepository.save(newUser);
        }

        throw null;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(null);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

}
