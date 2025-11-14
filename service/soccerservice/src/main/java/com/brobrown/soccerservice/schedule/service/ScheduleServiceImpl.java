package com.brobrown.soccerservice.schedule.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brobrown.soccerservice.common.Messenger;
import com.brobrown.soccerservice.schedule.domain.Schedule;
import com.brobrown.soccerservice.schedule.domain.ScheduleDTO;
import com.brobrown.soccerservice.schedule.repository.ScheduleRepository;
import com.brobrown.soccerservice.stadium.domain.Stadium;
import com.brobrown.soccerservice.stadium.repository.StadiumRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final StadiumRepository stadiumRepository;

    @Override
    public Messenger save(ScheduleDTO scheduleDTO) {
        try {
            Schedule schedule = dtoToEntity(scheduleDTO);
            @SuppressWarnings("null")
            Schedule saved = scheduleRepository.save(schedule);
            return Messenger.builder()
                    .code(200)
                    .message("Schedule saved successfully")
                    .data(saved)
                    .build();
        } catch (Exception e) {
            return Messenger.builder()
                    .code(500)
                    .message("Error saving schedule: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public Messenger update(ScheduleDTO scheduleDTO) {
        try {
            Long scheduleId = scheduleDTO.getId();
            if (scheduleId == null) {
                throw new RuntimeException("Schedule ID is required");
            }
            Schedule schedule = scheduleRepository.findById(scheduleId)
                    .orElseThrow(() -> new RuntimeException("Schedule not found"));
            updateEntityFromDto(schedule, scheduleDTO);
            @SuppressWarnings("null")
            Schedule updated = scheduleRepository.save(schedule);
            return Messenger.builder()
                    .code(200)
                    .message("Schedule updated successfully")
                    .data(updated)
                    .build();
        } catch (Exception e) {
            return Messenger.builder()
                    .code(500)
                    .message("Error updating schedule: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public Messenger delete(String id) {
        try {
            Long scheduleId = Long.parseLong(id);
            scheduleRepository.deleteById(scheduleId);
            return Messenger.builder()
                    .code(200)
                    .message("Schedule deleted successfully")
                    .build();
        } catch (Exception e) {
            return Messenger.builder()
                    .code(500)
                    .message("Error deleting schedule: " + e.getMessage())
                    .build();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Messenger findById(String id) {
        try {
            Long scheduleId = Long.parseLong(id);
            Schedule schedule = scheduleRepository.findById(scheduleId)
                    .orElseThrow(() -> new RuntimeException("Schedule not found"));
            return Messenger.builder()
                    .code(200)
                    .message("Schedule found")
                    .data(schedule)
                    .build();
        } catch (Exception e) {
            return Messenger.builder()
                    .code(500)
                    .message("Error finding schedule: " + e.getMessage())
                    .build();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Messenger findAll() {
        try {
            List<Schedule> schedules = scheduleRepository.findAll();
            return Messenger.builder()
                    .code(200)
                    .message("Schedules retrieved successfully")
                    .data(schedules)
                    .build();
        } catch (Exception e) {
            return Messenger.builder()
                    .code(500)
                    .message("Error retrieving schedules: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public Messenger saveAll(List<ScheduleDTO> scheduleDTOs) {
        try {
            List<Schedule> schedules = scheduleDTOs.stream()
                    .map(this::dtoToEntity)
                    .collect(Collectors.toList());
            @SuppressWarnings("null")
            List<Schedule> saved = scheduleRepository.saveAll(schedules);
            return Messenger.builder()
                    .code(200)
                    .message("Schedules saved successfully")
                    .data(saved)
                    .build();
        } catch (Exception e) {
            return Messenger.builder()
                    .code(500)
                    .message("Error saving schedules: " + e.getMessage())
                    .build();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Messenger findByKeyword(String keyword) {
        try {
            List<Schedule> schedules = scheduleRepository.findByKeyword(keyword);
            return Messenger.builder()
                    .code(200)
                    .message("Schedules found by keyword: " + keyword)
                    .data(schedules)
                    .build();
        } catch (Exception e) {
            return Messenger.builder()
                    .code(500)
                    .message("Error searching schedules: " + e.getMessage())
                    .build();
        }
    }

    private Schedule dtoToEntity(ScheduleDTO dto) {
        Schedule schedule = new Schedule();
        if (dto.getId() != null) {
            schedule.setId(dto.getId());
        }
        schedule.setScheDate(dto.getScheDate());
        schedule.setGubun(dto.getGubun());
        schedule.setHometeamId(dto.getHometeamId());
        schedule.setAwayteamId(dto.getAwayteamId());
        schedule.setHomeScore(dto.getHomeScore());
        schedule.setAwayScore(dto.getAwayScore());

        String stadiumUk = dto.getStadiumUk();
        if (stadiumUk != null && !stadiumUk.isEmpty()) {
            Stadium stadium = stadiumRepository.findById(stadiumUk)
                    .orElse(null);
            schedule.setStadium(stadium);
        }

        return schedule;
    }

    private void updateEntityFromDto(Schedule schedule, ScheduleDTO dto) {
        if (dto.getScheDate() != null)
            schedule.setScheDate(dto.getScheDate());
        if (dto.getGubun() != null)
            schedule.setGubun(dto.getGubun());
        if (dto.getHometeamId() != null)
            schedule.setHometeamId(dto.getHometeamId());
        if (dto.getAwayteamId() != null)
            schedule.setAwayteamId(dto.getAwayteamId());
        if (dto.getHomeScore() != null)
            schedule.setHomeScore(dto.getHomeScore());
        if (dto.getAwayScore() != null)
            schedule.setAwayScore(dto.getAwayScore());

        String stadiumUk = dto.getStadiumUk();
        if (stadiumUk != null && !stadiumUk.isEmpty()) {
            Stadium stadium = stadiumRepository.findById(stadiumUk)
                    .orElse(null);
            schedule.setStadium(stadium);
        }
    }
}
