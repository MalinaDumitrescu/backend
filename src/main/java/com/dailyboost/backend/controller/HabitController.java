package com.dailyboost.backend.controller;

import com.dailyboost.backend.model.Habit;
import com.dailyboost.backend.service.HabitService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/habits")
@CrossOrigin(origins = "*")
public class HabitController {

    private final HabitService habitService;

    public HabitController(HabitService habitService) {
        this.habitService = habitService;
    }

    @PostMapping
    public Habit addHabit(@RequestBody Habit habit, Authentication authentication) {
        String userId = (String) authentication.getPrincipal();
        habit.setUserId(userId);
        return habitService.addHabit(habit); // ⚙️ streak actualizat în service
    }

    @GetMapping
    public List<Habit> getUserHabits(Authentication authentication) {
        String userId = (String) authentication.getPrincipal();
        return habitService.getHabitsForUser(userId);
    }

    @PutMapping
    public Habit updateHabit(@RequestBody Habit habit, Authentication authentication) {
        String userId = (String) authentication.getPrincipal();
        habit.setUserId(userId);
        return habitService.updateHabit(habit); // ⚙️ streak actualizat în service
    }

    @DeleteMapping("/{habitId}")
    public void deleteHabit(@PathVariable String habitId, Authentication authentication) {
        String userId = (String) authentication.getPrincipal();
        habitService.deleteHabit(habitId, userId); // ⚙️ streak actualizat în service
    }
}
