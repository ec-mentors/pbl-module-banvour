package com.banvour.pomogatto.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class CreateOrUpdateSessionEntryDto {
    private LocalDateTime startTime;
    private int workDuration;
    private int breakDuration;
    private int breakCounter;
    private String notes;
    private Long workPresetId;
}