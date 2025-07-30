package com.banvour.pomogatto.service;

import com.banvour.pomogatto.dto.BreakActivityDto;
import com.banvour.pomogatto.dto.CreateOrUpdateBreakActivityDto;
import com.banvour.pomogatto.entity.BreakActivity;
import com.banvour.pomogatto.mapper.BreakActivityMapper;
import com.banvour.pomogatto.repository.BreakActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BreakActivityService {

    private final BreakActivityRepository breakActivityRepository;
    private final BreakActivityMapper breakActivityMapper;

    @Transactional(readOnly = true)
    public List<BreakActivityDto> getAllActivities() {
        return breakActivityRepository.findAllByOrderByIdAsc()
                .stream()
                .map(breakActivityMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BreakActivityDto getActivityById(Long id) {
        BreakActivity activity = breakActivityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Activity not found with id: " + id));
        return breakActivityMapper.toDto(activity);
    }

    @Transactional
    public BreakActivityDto createActivity(CreateOrUpdateBreakActivityDto dto) {
        BreakActivity activity = new BreakActivity();
        activity.setName(dto.getName());
        activity.setDescription(dto.getDescription());
        activity.setAlarmSound(dto.getAlarmSound());
        activity.setImageUrl(dto.getImageUrl());

        BreakActivity savedActivity = breakActivityRepository.save(activity);
        return breakActivityMapper.toDto(savedActivity);
    }

    @Transactional
    public BreakActivityDto updateActivity(Long id, CreateOrUpdateBreakActivityDto dto) {
        BreakActivity activity = breakActivityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Activity not found with id: " + id));

        activity.setName(dto.getName());
        activity.setDescription(dto.getDescription());
        activity.setAlarmSound(dto.getAlarmSound());
        activity.setImageUrl(dto.getImageUrl());

        BreakActivity updatedActivity = breakActivityRepository.save(activity);
        return breakActivityMapper.toDto(updatedActivity);
    }

    @Transactional
    public void deleteActivity(Long id) {
        if (!breakActivityRepository.existsById(id)) {
            throw new RuntimeException("Activity not found with id: " + id);
        }
        breakActivityRepository.deleteById(id);
    }

    // This new method is for populating our edit form.
    @Transactional(readOnly = true)
    public CreateOrUpdateBreakActivityDto getActivityForUpdate(Long id) {
        BreakActivity activity = breakActivityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Activity not found with id: " + id));

        CreateOrUpdateBreakActivityDto dto = new CreateOrUpdateBreakActivityDto();
        dto.setName(activity.getName());
        dto.setDescription(activity.getDescription());
        dto.setAlarmSound(activity.getAlarmSound());
        dto.setImageUrl(activity.getImageUrl());
//        dto.setHasCounter(activity.isHasCounter()); // Assuming you add this field to your form later

        return dto;
    }
}