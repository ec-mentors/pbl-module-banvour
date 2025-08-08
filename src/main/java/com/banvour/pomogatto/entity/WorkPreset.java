package com.banvour.pomogatto.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class WorkPreset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(nullable = false)
    private Long presetGroupId;
    @Column(nullable = false)
    private boolean active;
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private String name;
    private String description;
    private int workDuration;
    private int breakDuration;
    private int longBreakDuration;
    private String alarmSound;
    private String imageUrl;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "break_activity_id")
    private BreakActivity breakActivity;
}