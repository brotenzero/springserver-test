package com.brobrown.soccerservice.player;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brobrown.soccerservice.common.Messenger;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PlayerServiceImpl implements PlayerService {

    @Override
    public Messenger save(PlayerModel playerDTO) {
        return null;
    }

    @Override
    public Messenger update(PlayerModel playerDTO) {
        return null;
    }

    @Override
    public Messenger delete(String id) {
        return null;
    }

    @Override
    public Messenger findById(String id) {
        return null;
    }

    @Override
    public Messenger findAll() {
        return null;
    }

    @Override
    public Messenger saveAll(List<PlayerModel> playerDTOs) {
        return null;
    }

    @Override
    public Messenger findByKeyword(String keyword) {
        return null;
    }

}
