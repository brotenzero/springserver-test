package com.brobrown.soccerservice.stadium.service;

import java.util.List;

import com.brobrown.soccerservice.common.Messenger;
import com.brobrown.soccerservice.stadium.domain.StadiumDTO;

public interface StadiumService {
    Messenger save(StadiumDTO stadiumDTO);

    Messenger update(StadiumDTO stadiumDTO);

    Messenger delete(String id);

    Messenger findById(String id);

    Messenger findAll();

    Messenger saveAll(List<StadiumDTO> stadiumDTOs);

    Messenger findByKeyword(String keyword);

}

