package com.banvour.pomogatto.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class SessionEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDateTime startTime;
    private int workDuration;
    private int breakDuration;
    private int breakCounter;
    @Column(columnDefinition = "TEXT") // Use TEXT for potentially long notes
    private String notes;

    // --- Relationships ---
    @ManyToOne
    @JoinColumn(name = "preset_id") // Creates the 'presetId' foreign key
    private WorkPreset workPreset;

    @ManyToOne
    @JoinColumn(name = "activity_id") // Creates the 'activityId' foreign key
    private BreakActivity breakActivity;
}