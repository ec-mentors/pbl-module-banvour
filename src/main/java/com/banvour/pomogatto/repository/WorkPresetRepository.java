package com.banvour.pomogatto.repository;

import com.banvour.pomogatto.entity.WorkPreset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <h3>implements jpa methods like save(), findById() etc. automatically</h2>
 */
@Repository
public interface WorkPresetRepository extends JpaRepository<WorkPreset, Long> {
}
