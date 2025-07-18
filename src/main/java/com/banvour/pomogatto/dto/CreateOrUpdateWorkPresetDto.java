package com.banvour.pomogatto.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * <h6>input model for WorkPresetDto (no id)</h6>
 */
@Getter
@Setter
public class CreateOrUpdateWorkPresetDto {
    private String name;
    private String description;
    private int workDuration;
    private int breakDuration;
    private int longBreakDuration;
    private String alarmSound;
    private String imageUrl;
    private Long breakActivityId;
}