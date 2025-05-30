package com.dailyboost.backend.service;

import com.dailyboost.backend.model.Habit;
import com.dailyboost.backend.repository.HabitRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HabitService {

    private final HabitRepository habitRepository;
    private final UserService userService;

    public HabitService(HabitRepository habitRepository, UserService userService) {
        this.habitRepository = habitRepository;
        this.userService = userService;
    }

    public Habit addHabit(Habit habit) {
        if ("onetime".equals(habit.getFrequency()) && habit.getDate() == null) {
            throw new IllegalArgumentException("Onetime habits must have a date");
        }
        Habit saved = habitRepository.save(habit);
        userService.updateUserStreak(habit.getUserId());
        return saved;
    }


    public List<Habit> getHabitsForUser(String userId) {
        return habitRepository.findByUserId(userId);
    }

    public void deleteHabit(String habitId, String userId) {
        habitRepository.deleteById(habitId);
        userService.updateUserStreak(userId);
    }


    public Habit updateHabit(Habit habit) {
        if ("onetime".equals(habit.getFrequency()) && habit.getDate() == null) {
            throw new IllegalArgumentException("Onetime habits must have a date");
        }
        Habit updated = habitRepository.save(habit);
        userService.updateUserStreak(habit.getUserId());
        return updated;
    }
}
