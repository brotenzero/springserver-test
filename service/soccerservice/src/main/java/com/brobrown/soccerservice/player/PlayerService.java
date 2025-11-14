package com.brobrown.soccerservice.player;

import java.util.List;

import com.brobrown.soccerservice.common.Messenger;

public interface PlayerService {
    Messenger save(PlayerModel playerDTO);

    Messenger update(PlayerModel playerDTO);

    Messenger delete(String id);

    Messenger findById(String id);

    Messenger findAll();

    Messenger saveAll(List<PlayerModel> playerDTOs);

    Messenger findByKeyword(String keyword);

}
