package com.brobrown.soccerservice.team.service;

import java.util.List;

import com.brobrown.soccerservice.common.Messenger;
import com.brobrown.soccerservice.team.domain.TeamDTO;

public interface TeamService {
    Messenger save(TeamDTO teamDTO);

    Messenger update(TeamDTO teamDTO);

    Messenger delete(String id);

    Messenger findById(String id);

    Messenger findAll();

    Messenger saveAll(List<TeamDTO> teamDTOs);

    Messenger findByKeyword(String keyword);

}

