package com.dailyboost.backend.service;

import com.dailyboost.backend.model.Habit;
import com.dailyboost.backend.model.User;
import com.dailyboost.backend.repository.HabitRepository;
import com.dailyboost.backend.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import java.util.ArrayList;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final HabitRepository habitRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, HabitRepository habitRepository) {
        this.userRepository = userRepository;
        this.habitRepository = habitRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * Înregistrează un utilizator nou după validări.
     * Parola este criptată aici.
     */
    public User register(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }

        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username already in use");
        }

        // Criptează parola clară primită
        user.setPasswordHash(passwordEncoder.encode(user.getPasswordHash()));
        return userRepository.save(user);
    }

    /**
     * Găsește un utilizator după email.
     */
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> searchByUsername(String partialName) {
        return userRepository.findByUsernameContainingIgnoreCase(partialName);
    }

    public void removeFriend(String userId, String friendId) {
        userRepository.findById(userId).ifPresent(user -> {
            if (user.getFriendIds().remove(friendId)) {
                userRepository.save(user);
            }
        });
    }



    /**
     * Verifică dacă parola clară corespunde cu hash-ul stocat.
     */
    public boolean checkPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * Calculează streak-ul utilizatorului pe baza obiceiurilor completate consecutive.
     */
    public void updateUserStreak(String userId) {
        List<Habit> userHabits = habitRepository.findByUserId(userId);

        userRepository.findById(userId).ifPresent(user -> {
            if (userHabits.isEmpty()) {
                user.setStreak(0);
                userRepository.save(user);
                return;
            }

            LocalDate today = LocalDate.now();
            int calculatedStreak = 0;

            // Verifică consecutiv până la 365 zile înapoi
            for (int i = 0; i < 365; i++) {
                LocalDate date = today.minusDays(i);

                boolean allDone = userHabits.stream()
                        .allMatch(h -> h.getCompletedDates() != null && h.getCompletedDates().contains(date));

                if (allDone) {
                    calculatedStreak++;
                } else {
                    break;
                }
            }

            int previousStreak = user.getStreak();
            user.setStreak(calculatedStreak);
            userRepository.save(user);

            // ✅ Dacă streak-ul s-a îmbunătățit azi, trimitem notificări prietenilor
            if (calculatedStreak > previousStreak) {
                for (String friendId : user.getFriendIds()) {
                    userRepository.findById(friendId).ifPresent(friend -> {
                        List<String> notifications = friend.getStreakNotifications();
                        if (notifications == null) {
                            notifications = new ArrayList<>();
                        }
                        notifications.add(user.getUsername() + " și-a menținut streak-ul azi! 💪");
                        friend.setStreakNotifications(notifications);
                        userRepository.save(friend);
                    });
                }
            }


        });
    }


}

