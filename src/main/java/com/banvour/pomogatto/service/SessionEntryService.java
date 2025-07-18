package com.banvour.pomogatto.service;

import com.banvour.pomogatto.dto.CreateOrUpdateSessionEntryDto;
import com.banvour.pomogatto.dto.SessionEntryDto;
import com.banvour.pomogatto.entity.SessionEntry;
import com.banvour.pomogatto.entity.WorkPreset;
import com.banvour.pomogatto.mapper.SessionEntryMapper;
import com.banvour.pomogatto.repository.SessionEntryRepository;
import com.banvour.pomogatto.repository.WorkPresetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SessionEntryService {

    private final SessionEntryRepository sessionEntryRepository;
    private final WorkPresetRepository workPresetRepository;
    private final SessionEntryMapper sessionEntryMapper;

    @Transactional(readOnly = true)
    public List<SessionEntryDto> getAllSessionEntries() {
        return sessionEntryRepository.findAll()
                .stream()
                .map(sessionEntryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SessionEntryDto getSessionEntryById(Long id) {
        SessionEntry session = sessionEntryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SessionEntry not found with id: " + id));
        return sessionEntryMapper.toDto(session);
    }

    @Transactional
    public SessionEntryDto createSessionEntry(CreateOrUpdateSessionEntryDto dto) {
        SessionEntry session = new SessionEntry();
        mapDtoToEntity(dto, session);
        SessionEntry savedSession = sessionEntryRepository.save(session);
        return sessionEntryMapper.toDto(savedSession);
    }

    @Transactional
    public SessionEntryDto updateSessionEntry(Long id, CreateOrUpdateSessionEntryDto dto) {
        SessionEntry session = sessionEntryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SessionEntry not found with id: " + id));

        mapDtoToEntity(dto, session);
        SessionEntry updatedSession = sessionEntryRepository.save(session);
        return sessionEntryMapper.toDto(updatedSession);
    }

    @Transactional
    public void deleteSessionEntry(Long id) {
        if (!sessionEntryRepository.existsById(id)) {
            throw new RuntimeException("SessionEntry not found with id: " + id);
        }
        sessionEntryRepository.deleteById(id);
    }

    private void mapDtoToEntity(CreateOrUpdateSessionEntryDto dto, SessionEntry entity) {
        WorkPreset workPreset = workPresetRepository.findById(dto.getWorkPresetId())
                .orElseThrow(() -> new RuntimeException("WorkPreset not found with id: " + dto.getWorkPresetId()));

        entity.setStartTime(dto.getStartTime());
        entity.setWorkDuration(dto.getWorkDuration());
        entity.setBreakDuration(dto.getBreakDuration());
        entity.setBreakCounter(dto.getBreakCounter());
        entity.setNotes(dto.getNotes());
        entity.setWorkPreset(workPreset);
    }
}