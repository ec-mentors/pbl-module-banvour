package com.banvour.pomogatto.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * <h6>input model for BreakActivityDto (no id)</h6>
 */

@Getter
@Setter
public class CreateOrUpdateBreakActivityDto {
    private String name;
    private String description;
    private String alarmSound;
    private String imageUrl;
    private boolean hasCounter;
}