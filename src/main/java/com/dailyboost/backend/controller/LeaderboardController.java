package com.dailyboost.backend.controller;

import com.dailyboost.backend.model.User;
import com.dailyboost.backend.repository.UserRepository;
import com.dailyboost.backend.security.JwtUtil;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/leaderboard")
@CrossOrigin(origins = "http://localhost:5173")
public class LeaderboardController {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public LeaderboardController(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping
    public List<Map<String, Object>> getLeaderboard(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String userId = jwtUtil.validateTokenAndGetUserId(token);

        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) return Collections.emptyList();

        User currentUser = userOpt.get();

        List<String> friendIds = new ArrayList<>(currentUser.getFriendIds());

        if (!friendIds.contains(userId)) {
            friendIds.add(userId);
        }

        List<User> friends = userRepository.findAllById(friendIds);

        return friends.stream()
                .map(friend -> {
                    Map<String, Object> userMap = new HashMap<>();
                    userMap.put("id", friend.getId());
                    userMap.put("username", friend.getUsername());
                    userMap.put("avatarUrl", friend.getAvatarUrl());
                    userMap.put("streak", friend.getStreak());
                    return userMap;
                })
                .sorted(Comparator.comparingInt(u -> ((Integer) ((Map<String, Object>) u).get("streak"))).reversed())
                .collect(Collectors.toList());


    }

    @PostMapping("/add-friend")
    public String addFriend(@RequestHeader("Authorization") String authHeader, @RequestBody Map<String, String> body) {
        String token = authHeader.replace("Bearer ", "");
        String userId = jwtUtil.validateTokenAndGetUserId(token);

        String friendUsername = body.get("username"); // ✅ așteaptă `username`

        Optional<User> userOpt = userRepository.findById(userId);
        Optional<User> friendOpt = userRepository.findByUsername(friendUsername); // ✅ caută după username

        if (userOpt.isEmpty() || friendOpt.isEmpty()) return "User not found";

        User user = userOpt.get();
        User friend = friendOpt.get();

        if (!user.getFriendIds().contains(friend.getId())) {
            user.getFriendIds().add(friend.getId());
            userRepository.save(user);
        }

        return "Friend added!";
    }


    @PostMapping("/send-kudos")
    public String sendKudos(@RequestHeader("Authorization") String authHeader, @RequestBody Map<String, String> body) {
        String token = authHeader.replace("Bearer ", "");
        String senderId = jwtUtil.validateTokenAndGetUserId(token);

        String toUserId = body.get("toUserId");
        String message = body.get("message");

        Optional<User> senderOpt = userRepository.findById(senderId);
        Optional<User> targetOpt = userRepository.findById(toUserId);
        if (senderOpt.isEmpty() || targetOpt.isEmpty()) return "User not found";

        User sender = senderOpt.get();
        User recipient = targetOpt.get();

        String notif = "💌 Kudos de la " + sender.getUsername() + ": " + message;
        recipient.getStreakNotifications().add(notif);

        userRepository.save(recipient);
        return "Kudos notification sent!";
    }

    @GetMapping("/notifications")
    public List<String> getNotifications(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String userId = jwtUtil.validateTokenAndGetUserId(token);

        return userRepository.findById(userId)
                .map(User::getStreakNotifications)
                .orElse(List.of());
    }

    @PostMapping("/notifications/clear")
    public void clearNotifications(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        String userId = jwtUtil.validateTokenAndGetUserId(token);

        userRepository.findById(userId).ifPresent(user -> {
            user.setStreakNotifications(new ArrayList<>());
            userRepository.save(user);
        });
    }

    @DeleteMapping("/remove-friend/{friendId}")
    public String removeFriend(@RequestHeader("Authorization") String authHeader, @PathVariable String friendId) {
        String token = authHeader.replace("Bearer ", "");
        String userId = jwtUtil.validateTokenAndGetUserId(token);

        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) return "User not found";

        User user = userOpt.get();
        boolean removed = user.getFriendIds().remove(friendId);
        userRepository.save(user);

        return removed ? "Friend removed!" : "Friend not in your list";
    }
}
