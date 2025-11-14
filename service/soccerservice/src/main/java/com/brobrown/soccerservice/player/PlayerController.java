package com.brobrown.soccerservice.player;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brobrown.soccerservice.common.Messenger;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/player")

public class PlayerController {
    private final PlayerService playerService;


    @PostMapping("/save")
    public ResponseEntity<Messenger> save(@RequestBody PlayerModel playerDTO) {
        return ResponseEntity.ok(playerService.save(playerDTO));
    }

    @PostMapping("/all")
    public ResponseEntity<Messenger> saveAll(@RequestBody List<PlayerModel> playerDTOs) {
        return ResponseEntity.ok(playerService.saveAll(playerDTOs));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Messenger> update(@PathVariable String id, @RequestBody PlayerModel playerDTO) {
        return ResponseEntity.ok(playerService.update(playerDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Messenger> delete(@PathVariable String id) {
        return ResponseEntity.ok(playerService.delete(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Messenger> findById(@PathVariable String id) {
        return ResponseEntity.ok(playerService.findById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<Messenger> findAll() {
        return ResponseEntity.ok(playerService.findAll());
    }
}
