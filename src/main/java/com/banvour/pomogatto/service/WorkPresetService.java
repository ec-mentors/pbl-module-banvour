package com.banvour.pomogatto.service;

import com.banvour.pomogatto.dto.CreateOrUpdateWorkPresetDto;
import com.banvour.pomogatto.dto.WorkPresetDto;
import com.banvour.pomogatto.entity.BreakActivity;
import com.banvour.pomogatto.entity.WorkPreset;
import com.banvour.pomogatto.mapper.WorkPresetMapper;
import com.banvour.pomogatto.repository.BreakActivityRepository;
import com.banvour.pomogatto.repository.WorkPresetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkPresetService {

    private final WorkPresetRepository workPresetRepository;
    private final BreakActivityRepository breakActivityRepository;
    private final WorkPresetMapper workPresetMapper;

    @Transactional(readOnly = true)
    public List<WorkPresetDto> getAllPresets() {
        return workPresetRepository.findAll()
                .stream()
                .map(workPresetMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public WorkPresetDto getPresetById(Long id) {
        WorkPreset workPreset = workPresetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Preset not found with id: " + id));
        return workPresetMapper.toDto(workPreset);
    }

    @Transactional
    public WorkPresetDto createPreset(CreateOrUpdateWorkPresetDto dto) {
        WorkPreset workPreset = new WorkPreset();
        mapDtoToEntity(dto, workPreset);
        WorkPreset savedPreset = workPresetRepository.save(workPreset);
        return workPresetMapper.toDto(savedPreset);
    }

    @Transactional
    public WorkPresetDto updatePreset(Long id, CreateOrUpdateWorkPresetDto dto) {
        WorkPreset workPreset = workPresetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Preset not found with id: " + id));
        mapDtoToEntity(dto, workPreset);
        WorkPreset updatedPreset = workPresetRepository.save(workPreset);
        return workPresetMapper.toDto(updatedPreset);
    }

    @Transactional
    public void deletePreset(Long id) {
        if (!workPresetRepository.existsById(id)) {
            throw new RuntimeException("Preset not found with id: " + id);
        }
        workPresetRepository.deleteById(id);
    }

    private void mapDtoToEntity(CreateOrUpdateWorkPresetDto dto, WorkPreset entity) {
        BreakActivity breakActivity = breakActivityRepository.findById(dto.getBreakActivityId())
                .orElseThrow(() -> new RuntimeException("BreakActivity not found with id: " + dto.getBreakActivityId()));

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setWorkDuration(dto.getWorkDuration());
        entity.setBreakDuration(dto.getBreakDuration());
        entity.setLongBreakDuration(dto.getLongBreakDuration());
        entity.setAlarmSound(dto.getAlarmSound());
        entity.setImageUrl(dto.getImageUrl());
        entity.setBreakActivity(breakActivity);
    }
}