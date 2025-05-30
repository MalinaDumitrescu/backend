package com.dailyboost.backend.service;

import com.dailyboost.backend.model.JournalEntry;
import com.dailyboost.backend.repository.JournalEntryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class JournalService {

    private final JournalEntryRepository journalEntryRepository;

    public JournalService(JournalEntryRepository journalEntryRepository) {
        this.journalEntryRepository = journalEntryRepository;
    }

    public JournalEntry createEntry(JournalEntry entry) {
        entry.setCreatedAt(LocalDateTime.now());

        // Dacă nu este setată o zi explicită, se folosește ziua curentă
        if (entry.getEntryDate() == null) {
            entry.setEntryDate(LocalDate.now());
        }

        return journalEntryRepository.save(entry);
    }

    public List<JournalEntry> getUserEntries(String userId) {
        return journalEntryRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public JournalEntry updateEntry(JournalEntry entry) {
        // Nu suprascriem entryDate dacă deja e setat
        if (entry.getEntryDate() == null) {
            entry.setEntryDate(LocalDate.now());
        }

        return journalEntryRepository.save(entry);
    }

    public void deleteEntry(String id) {
        journalEntryRepository.deleteById(id);
    }
}
