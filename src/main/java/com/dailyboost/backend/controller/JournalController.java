package com.dailyboost.backend.controller;

import com.dailyboost.backend.model.JournalEntry;
import com.dailyboost.backend.service.JournalService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/journal")
@CrossOrigin(origins = "*")
public class JournalController {

    private final JournalService journalService;

    public JournalController(JournalService journalService) {
        this.journalService = journalService;
    }

    @PostMapping
    public JournalEntry createEntry(@RequestBody JournalEntry entry, Authentication authentication) {
        String userId = (String) authentication.getPrincipal();
        entry.setUserId(userId);
        return journalService.createEntry(entry);
    }

    @GetMapping
    public List<JournalEntry> getUserEntries(Authentication authentication) {
        String userId = (String) authentication.getPrincipal();
        return journalService.getUserEntries(userId);
    }

    // 🔄 Actualizare Jurnal prin ID (folosit de frontend)
    @PutMapping("/{id}")
    public JournalEntry updateEntry(@PathVariable String id,
                                    @RequestBody JournalEntry entry,
                                    Authentication authentication) {
        String userId = (String) authentication.getPrincipal();
        entry.setUserId(userId); // prevenim modificarea altor useri
        entry.setId(id);         // ne asigurăm că ID-ul e setat corect
        return journalService.updateEntry(entry);
    }

    @DeleteMapping("/{id}")
    public void deleteEntry(@PathVariable String id) {
        journalService.deleteEntry(id);
    }
}
