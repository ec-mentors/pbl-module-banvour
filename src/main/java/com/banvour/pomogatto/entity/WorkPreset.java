package com.banvour.pomogatto.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class WorkPreset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    private String description;
    private int workDuration;
    private int breakDuration;
    private int longBreakDuration;
    private String alarmSound;
    private String imageUrl;

    // --- This is the Relationship ---
    // @ManyToOne: This annotation tells JPA that many "WorkPreset" entries can be related to one "BreakActivity".
    @ManyToOne
    // @JoinColumn: This specifies the foreign key column in the database table.
    // This will create a column named "break_activity_id" in the WorkPreset table,
    @JoinColumn(name = "break_activity_id")
    private BreakActivity breakActivity;
}