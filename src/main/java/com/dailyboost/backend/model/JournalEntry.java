package com.dailyboost.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "journal_entries")
public class JournalEntry {

    @Id
    private String id;

    private String userId;
    private String title;
    private String content;
    private String mood; // opțional: "happy", "sad", etc.
    private String imageUrl; // opțional: link spre poză
    private LocalDateTime createdAt;
    private LocalDate entryDate; // nou câmp pentru sincronizarea corectă a mood-ului în calendar

    public JournalEntry() {}

    public JournalEntry(String userId, String title, String content, String mood, String imageUrl,
                        LocalDateTime createdAt, LocalDate entryDate) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.mood = mood;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
        this.entryDate = entryDate;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getMood() {
        return mood;
    }
    public void setMood(String mood) {
        this.mood = mood;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }
    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    @Override
    public String toString() {
        return "JournalEntry{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", mood='" + mood + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", createdAt=" + createdAt +
                ", entryDate=" + entryDate +
                '}';
    }
}
