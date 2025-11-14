package com.brobrown.soccerservice.schedule.service;

import java.util.List;

import com.brobrown.soccerservice.common.Messenger;
import com.brobrown.soccerservice.schedule.domain.ScheduleDTO;

public interface ScheduleService {
    Messenger save(ScheduleDTO scheduleDTO);

    Messenger update(ScheduleDTO scheduleDTO);

    Messenger delete(String id);

    Messenger findById(String id);

    Messenger findAll();

    Messenger saveAll(List<ScheduleDTO> scheduleDTOs);

    Messenger findByKeyword(String keyword);

}






