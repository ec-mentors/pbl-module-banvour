package com.banvour.pomogatto.mapper;

import com.banvour.pomogatto.dto.WorkPresetDto;
import com.banvour.pomogatto.entity.WorkPreset;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkPresetMapper {

    private final BreakActivityMapper breakActivityMapper;

    public WorkPresetDto toDto(WorkPreset entity) {
        if (entity == null) {
            return null;
        }

        WorkPresetDto dto = new WorkPresetDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setWorkDuration(entity.getWorkDuration());
        dto.setBreakDuration(entity.getBreakDuration());
        dto.setLongBreakDuration(entity.getLongBreakDuration());
        dto.setAlarmSound(entity.getAlarmSound());
        dto.setImageUrl(entity.getImageUrl());

        dto.setBreakActivity(breakActivityMapper.toDto(entity.getBreakActivity()));

        return dto;
    }
}