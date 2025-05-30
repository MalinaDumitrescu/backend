package com.dailyboost.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "habits")
public class Habit {

    @Id
    private String id;

    private String userId;
    private String name;
    private String frequency; // "daily" sau "onetime"

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private List<LocalDate> completedDates;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date; // doar pentru onetime

    public Habit() {}

    public Habit(String userId, String name, String frequency, List<LocalDate> completedDates, LocalDate date) {
        this.userId = userId;
        this.name = name;
        this.frequency = frequency;
        this.completedDates = completedDates;
        this.date = date;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getFrequency() { return frequency; }
    public void setFrequency(String frequency) { this.frequency = frequency; }

    public List<LocalDate> getCompletedDates() { return completedDates; }
    public void setCompletedDates(List<LocalDate> completedDates) { this.completedDates = completedDates; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    @Override
    public String toString() {
        return "Habit{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                ", frequency='" + frequency + '\'' +
                ", completedDates=" + completedDates +
                ", date=" + date +
                '}';
    }
}
