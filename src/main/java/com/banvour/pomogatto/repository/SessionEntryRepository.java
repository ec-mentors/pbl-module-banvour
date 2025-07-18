package com.banvour.pomogatto.repository;

import com.banvour.pomogatto.entity.SessionEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionEntryRepository extends JpaRepository<SessionEntry, Long> {
}