package com.brobrown.soccerservice.schedule.controller;

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
import com.brobrown.soccerservice.schedule.domain.ScheduleDTO;
import com.brobrown.soccerservice.schedule.service.ScheduleService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedule")

public class ScheduleController {
    private final ScheduleService scheduleService;


    @PostMapping("/save")
    public ResponseEntity<Messenger> save(@RequestBody ScheduleDTO scheduleDTO) {
        return ResponseEntity.ok(scheduleService.save(scheduleDTO));
    }

    @PostMapping("/all")
    public ResponseEntity<Messenger> saveAll(@RequestBody List<ScheduleDTO> scheduleDTOs) {
        return ResponseEntity.ok(scheduleService.saveAll(scheduleDTOs));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Messenger> update(@PathVariable String id, @RequestBody ScheduleDTO scheduleDTO) {
        return ResponseEntity.ok(scheduleService.update(scheduleDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Messenger> delete(@PathVariable String id) {
        return ResponseEntity.ok(scheduleService.delete(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Messenger> findById(@PathVariable String id) {
        return ResponseEntity.ok(scheduleService.findById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<Messenger> findAll() {
        return ResponseEntity.ok(scheduleService.findAll());
    }
}

