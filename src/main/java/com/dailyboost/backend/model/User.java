package com.dailyboost.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
public class User {
    @Id
    private String id;

    private String username;
    private String email;
    private String passwordHash;
    private String avatarUrl;

    private List<String> friendIds = new ArrayList<>();
    private int streak = 0;

    private List<String> streakNotifications = new ArrayList<>();


    public User() {}

    public User(String username, String email, String passwordHash, String avatarUrl) {
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.avatarUrl = avatarUrl;
    }


    public List<String> getStreakNotifications() { return streakNotifications; }
    public void setStreakNotifications(List<String> streakNotifications) { this.streakNotifications = streakNotifications; }

    // Getteri și setteri (sau poți folosi Lombok mai târziu)
    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    // 🔽 Getteri și setteri noi
    public List<String> getFriendIds() { return friendIds; }
    public void setFriendIds(List<String> friendIds) { this.friendIds = friendIds; }

    public int getStreak() { return streak; }
    public void setStreak(int streak) { this.streak = streak; }


}

