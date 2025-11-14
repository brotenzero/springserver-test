package com.brobrown.soccerservice.team.controller;

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
import com.brobrown.soccerservice.team.domain.TeamDTO;
import com.brobrown.soccerservice.team.service.TeamService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/team")

public class TeamController {
    private final TeamService teamService;

    @PostMapping("/save")
    public ResponseEntity<Messenger> save(@RequestBody TeamDTO teamDTO) {
        return ResponseEntity.ok(teamService.save(teamDTO));
    }

    @PostMapping("/all")
    public ResponseEntity<Messenger> saveAll(@RequestBody List<TeamDTO> teamDTOs) {
        return ResponseEntity.ok(teamService.saveAll(teamDTOs));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Messenger> update(@PathVariable String id, @RequestBody TeamDTO teamDTO) {
        return ResponseEntity.ok(teamService.update(teamDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Messenger> delete(@PathVariable String id) {
        return ResponseEntity.ok(teamService.delete(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Messenger> findById(@PathVariable String id) {
        return ResponseEntity.ok(teamService.findById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<Messenger> findAll() {
        return ResponseEntity.ok(teamService.findAll());
    }
}
