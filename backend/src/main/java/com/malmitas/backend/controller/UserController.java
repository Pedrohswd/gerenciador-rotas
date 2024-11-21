package com.malmitas.backend.controller;

import com.malmitas.backend.model.User;
import com.malmitas.backend.model.dtos.UserDTO;
import com.malmitas.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<User> save(@RequestBody UserDTO user) {
        return ResponseEntity.ok(userService.save(user));
    }

}
