package com.banvour.pomogatto.mapper;

import com.banvour.pomogatto.dto.BreakActivityDto;
import com.banvour.pomogatto.entity.BreakActivity;
import org.springframework.stereotype.Component;

@Component
public class BreakActivityMapper {

    public BreakActivityDto toDto(BreakActivity entity) {
        if (entity == null) {
            return null;
        }

        BreakActivityDto dto = new BreakActivityDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setAlarmSound(entity.getAlarmSound());
        dto.setImageUrl(entity.getImageUrl());

        return dto;
    }
}