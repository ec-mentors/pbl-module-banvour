package com.banvour.pomogatto.dto;

import lombok.Getter;
import lombok.Setter;

/**
 *<h6>view model of WorkPreset entity</h6>
 */

@Getter
@Setter
public class WorkPresetDto {
    private Long id;
    private String name;
    private String description;
    private int workDuration;
    private int breakDuration;
    private int longBreakDuration;
    private String alarmSound;
    private String imageUrl;
    private BreakActivityDto breakActivity;
}
