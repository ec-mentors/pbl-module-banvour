package com.banvour.pomogatto.controller;

import com.banvour.pomogatto.dto.CreateOrUpdateWorkPresetDto;
import com.banvour.pomogatto.dto.WorkPresetDto;
import com.banvour.pomogatto.service.WorkPresetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/presets")
@RequiredArgsConstructor
public class WorkPresetController {

    private final WorkPresetService workPresetService;

    @GetMapping
    public ResponseEntity<List<WorkPresetDto>> getAllPresets() {
        return ResponseEntity.ok(workPresetService.getAllPresets());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkPresetDto> getPresetById(@PathVariable Long id) {
        return ResponseEntity.ok(workPresetService.getPresetById(id));
    }

    @PostMapping
    public ResponseEntity<WorkPresetDto> createPreset(@RequestBody CreateOrUpdateWorkPresetDto dto) {
        WorkPresetDto createdPreset = workPresetService.createPreset(dto);
        return new ResponseEntity<>(createdPreset, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkPresetDto> updatePreset(@PathVariable Long id, @RequestBody CreateOrUpdateWorkPresetDto dto) {
        return ResponseEntity.ok(workPresetService.updatePreset(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePreset(@PathVariable Long id) {
        workPresetService.deletePreset(id);
        return ResponseEntity.noContent().build();
    }
}