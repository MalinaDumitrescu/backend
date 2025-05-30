package com.dailyboost.backend.controller;

import com.dailyboost.backend.model.User;
import com.dailyboost.backend.security.JwtUtil;
import com.dailyboost.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    // DTO simplu pentru înregistrare
    public static class RegisterRequest {
        private String username;
        private String email;
        private String password;
        private String avatarUrl;

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }

        public String getAvatarUrl() { return avatarUrl; }
        public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
    }

    // DTO simplu pentru login
    public static class LoginRequest {
        private String email;
        private String password;

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest request) {
        try {
            User user = new User();
            user.setUsername(request.getUsername());
            user.setEmail(request.getEmail());
            user.setAvatarUrl(request.getAvatarUrl());
            user.setPasswordHash(request.getPassword()); // parola clară, hash-uită în UserService

            User saved = userService.register(user);
            return ResponseEntity.ok(saved);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginData) {
        Optional<User> userOpt = userService.findByEmail(loginData.getEmail());

        if (userOpt.isEmpty() || !userService.checkPassword(loginData.getPassword(), userOpt.get().getPasswordHash())) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        String token = jwtUtil.generateToken(userOpt.get().getId());
        return ResponseEntity.ok(Map.of("token", token));
    }

    @GetMapping("/search")
    public List<Map<String, String>> searchUsers(@RequestParam("q") String query) {
        return userService.searchByUsername(query).stream()
                .map(user -> Map.of(
                        "id", user.getId(),
                        "username", user.getUsername()
                ))
                .collect(Collectors.toList());
    }

}
