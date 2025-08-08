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

import java.time.LocalDateTime;
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
        return workPresetRepository.findByActiveOrderByPresetGroupIdAscIdAsc(true)
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
        workPreset.setActive(true);
        workPreset.setCreatedAt(LocalDateTime.now());
        mapDtoToEntity(dto, workPreset);
        WorkPreset initialSave = workPresetRepository.save(workPreset);
        initialSave.setPresetGroupId(initialSave.getId());
        WorkPreset finalSave = workPresetRepository.save(initialSave);
        return workPresetMapper.toDto(finalSave);
    }

    @Transactional
    public WorkPresetDto updatePreset(Long idOfOldVersion, CreateOrUpdateWorkPresetDto dto) {
        // 1. Find the old version that is being edited.
        WorkPreset oldVersion = workPresetRepository.findById(idOfOldVersion)
                .orElseThrow(() -> new RuntimeException("Preset version to update not found with id: " + idOfOldVersion));

        // 2. Deactivate the old version.
        oldVersion.setActive(false);
        workPresetRepository.save(oldVersion);

        // 3. Create a new WorkPreset instance for the new version.
        WorkPreset newVersion = new WorkPreset();

        newVersion.setActive(true);
        newVersion.setCreatedAt(LocalDateTime.now());

        // 4. Map the new data onto this new instance.
        mapDtoToEntity(dto, newVersion);

        // 5. Crucially, link the new version to the same group as the old one.
        newVersion.setPresetGroupId(oldVersion.getPresetGroupId());

        // 6. Save the new version. It will automatically be active and get a new ID.
        WorkPreset savedNewVersion = workPresetRepository.save(newVersion);

        return workPresetMapper.toDto(savedNewVersion);
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

    // This new method is specifically for populating our edit form.
    @Transactional(readOnly = true)
    public CreateOrUpdateWorkPresetDto getPresetForUpdate(Long id) {
        WorkPreset workPreset = workPresetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Preset not found with id: " + id));

        CreateOrUpdateWorkPresetDto dto = new CreateOrUpdateWorkPresetDto();
        dto.setName(workPreset.getName());
        dto.setDescription(workPreset.getDescription());
        dto.setWorkDuration(workPreset.getWorkDuration());
        dto.setBreakDuration(workPreset.getBreakDuration());
        dto.setLongBreakDuration(workPreset.getLongBreakDuration());
        dto.setAlarmSound(workPreset.getAlarmSound());
        dto.setImageUrl(workPreset.getImageUrl());

        // For the dropdown, we just need the ID of the associated activity
        if (workPreset.getBreakActivity() != null) {
            dto.setBreakActivityId(workPreset.getBreakActivity().getId());
        }

        return dto;
    }
}