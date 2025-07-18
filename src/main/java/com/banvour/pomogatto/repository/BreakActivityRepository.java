package com.banvour.pomogatto.repository;

import com.banvour.pomogatto.entity.BreakActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BreakActivityRepository extends JpaRepository<BreakActivity, Long> {
}