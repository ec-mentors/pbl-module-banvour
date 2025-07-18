package com.banvour.pomogatto.dto;

import lombok.Getter;
import lombok.Setter;

/**
 *<h6>view model of BreakActivity entity</h6>
 */

@Getter
@Setter
public class BreakActivityDto {
    private Long id;
    private String name;
    private String description;
    private String alarmSound;
    private String imageUrl;
    private boolean hasCounter;
}
