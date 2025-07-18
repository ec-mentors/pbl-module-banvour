package com.banvour.pomogatto.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 *<h6>view model of SessionEntry entity</h6>
 */

@Getter
@Setter
public class SessionEntryDto {
    private Long id;
    private LocalDateTime startTime;
    private int workDuration;
    private int breakDuration;
    private int breakCounter;
    private String notes;
    private WorkPresetDto workPreset;
}