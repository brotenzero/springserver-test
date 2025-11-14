package com.brobrown.soccerservice.stadium.controller;

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
import com.brobrown.soccerservice.stadium.domain.StadiumDTO;
import com.brobrown.soccerservice.stadium.service.StadiumService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stadium")

public class StadiumController {
    private final StadiumService stadiumService;

    @PostMapping("/save")
    public ResponseEntity<Messenger> save(@RequestBody StadiumDTO stadiumDTO) {
        return ResponseEntity.ok(stadiumService.save(stadiumDTO));
    }

    @PostMapping("/all")
    public ResponseEntity<Messenger> saveAll(@RequestBody List<StadiumDTO> stadiumDTOs) {
        return ResponseEntity.ok(stadiumService.saveAll(stadiumDTOs));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Messenger> update(@PathVariable String id, @RequestBody StadiumDTO stadiumDTO) {
        return ResponseEntity.ok(stadiumService.update(stadiumDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Messenger> delete(@PathVariable String id) {
        return ResponseEntity.ok(stadiumService.delete(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Messenger> findById(@PathVariable String id) {
        return ResponseEntity.ok(stadiumService.findById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<Messenger> findAll() {
        return ResponseEntity.ok(stadiumService.findAll());
    }
}
