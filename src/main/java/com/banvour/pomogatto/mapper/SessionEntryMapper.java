package com.banvour.pomogatto.mapper;

import com.banvour.pomogatto.dto.SessionEntryDto;
import com.banvour.pomogatto.entity.SessionEntry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SessionEntryMapper {

    private final WorkPresetMapper workPresetMapper;

    public SessionEntryDto toDto(SessionEntry entity) {
        if (entity == null) {
            return null;
        }

        SessionEntryDto dto = new SessionEntryDto();
        dto.setId(entity.getId());
        dto.setStartTime(entity.getStartTime());
        dto.setWorkDuration(entity.getWorkDuration());
        dto.setBreakDuration(entity.getBreakDuration());
        dto.setBreakCounter(entity.getBreakCounter());
        dto.setNotes(entity.getNotes());
        dto.setWorkPreset(workPresetMapper.toDto(entity.getWorkPreset()));

        return dto;
    }
}