package com.banvour.pomogatto.controller;

import com.banvour.pomogatto.dto.BreakActivityDto;
import com.banvour.pomogatto.dto.CreateOrUpdateBreakActivityDto;
import com.banvour.pomogatto.service.BreakActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
@RequiredArgsConstructor
public class BreakActivityController {

    private final BreakActivityService breakActivityService;

    @GetMapping
    public ResponseEntity<List<BreakActivityDto>> getAllActivities() {
        return ResponseEntity.ok(breakActivityService.getAllActivities());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BreakActivityDto> getActivityById(@PathVariable Long id) {
        return ResponseEntity.ok(breakActivityService.getActivityById(id));
    }

    @PostMapping
    public ResponseEntity<BreakActivityDto> createActivity(@RequestBody CreateOrUpdateBreakActivityDto dto) {
        BreakActivityDto createdActivity = breakActivityService.createActivity(dto);
        return new ResponseEntity<>(createdActivity, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BreakActivityDto> updateActivity(@PathVariable Long id, @RequestBody CreateOrUpdateBreakActivityDto dto) {
        return ResponseEntity.ok(breakActivityService.updateActivity(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteActivity(@PathVariable Long id) {
        breakActivityService.deleteActivity(id);
        return ResponseEntity.noContent().build();
    }
}