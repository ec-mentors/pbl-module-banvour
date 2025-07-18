package com.banvour.pomogatto.controller;

import com.banvour.pomogatto.dto.CreateOrUpdateSessionEntryDto;
import com.banvour.pomogatto.dto.SessionEntryDto;
import com.banvour.pomogatto.service.SessionEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions")
@RequiredArgsConstructor
public class SessionEntryController {

    private final SessionEntryService sessionEntryService;

    @GetMapping
    public ResponseEntity<List<SessionEntryDto>> getAllSessionEntries() {
        return ResponseEntity.ok(sessionEntryService.getAllSessionEntries());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SessionEntryDto> getSessionEntryById(@PathVariable Long id) {
        return ResponseEntity.ok(sessionEntryService.getSessionEntryById(id));
    }

    @PostMapping
    public ResponseEntity<SessionEntryDto> createSessionEntry(@RequestBody CreateOrUpdateSessionEntryDto dto) {
        SessionEntryDto createdSession = sessionEntryService.createSessionEntry(dto);
        return new ResponseEntity<>(createdSession, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SessionEntryDto> updateSessionEntry(@PathVariable Long id, @RequestBody CreateOrUpdateSessionEntryDto dto) {
        return ResponseEntity.ok(sessionEntryService.updateSessionEntry(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSessionEntry(@PathVariable Long id) {
        sessionEntryService.deleteSessionEntry(id);
        return ResponseEntity.noContent().build();
    }
}