package com.brobrown.soccerservice.team.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brobrown.soccerservice.common.Messenger;
import com.brobrown.soccerservice.stadium.domain.Stadium;
import com.brobrown.soccerservice.stadium.repository.StadiumRepository;
import com.brobrown.soccerservice.team.domain.Team;
import com.brobrown.soccerservice.team.domain.TeamDTO;
import com.brobrown.soccerservice.team.repository.TeamRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final StadiumRepository stadiumRepository;

    @Override
    public Messenger save(TeamDTO teamDTO) {
        try {
            Team team = dtoToEntity(teamDTO);
            @SuppressWarnings("null")
            Team saved = teamRepository.save(team);
            return Messenger.builder()
                    .code(200)
                    .message("Team saved successfully")
                    .data(saved)
                    .build();
        } catch (Exception e) {
            return Messenger.builder()
                    .code(500)
                    .message("Error saving team: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public Messenger update(TeamDTO teamDTO) {
        try {
            String teamId = teamDTO.getTeamId();
            if (teamId == null || teamId.isEmpty()) {
                throw new RuntimeException("Team ID is required");
            }
            Team team = teamRepository.findById(teamId)
                    .orElseThrow(() -> new RuntimeException("Team not found"));
            updateEntityFromDto(team, teamDTO);
            @SuppressWarnings("null")
            Team updated = teamRepository.save(team);
            return Messenger.builder()
                    .code(200)
                    .message("Team updated successfully")
                    .data(updated)
                    .build();
        } catch (Exception e) {
            return Messenger.builder()
                    .code(500)
                    .message("Error updating team: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public Messenger delete(String id) {
        try {
            if (id == null || id.isEmpty()) {
                throw new RuntimeException("Team ID is required");
            }
            teamRepository.deleteById(id);
            return Messenger.builder()
                    .code(200)
                    .message("Team deleted successfully")
                    .build();
        } catch (Exception e) {
            return Messenger.builder()
                    .code(500)
                    .message("Error deleting team: " + e.getMessage())
                    .build();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Messenger findById(String id) {
        try {
            if (id == null || id.isEmpty()) {
                throw new RuntimeException("Team ID is required");
            }
            Team team = teamRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Team not found"));
            return Messenger.builder()
                    .code(200)
                    .message("Team found")
                    .data(team)
                    .build();
        } catch (Exception e) {
            return Messenger.builder()
                    .code(500)
                    .message("Error finding team: " + e.getMessage())
                    .build();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Messenger findAll() {
        try {
            List<Team> teams = teamRepository.findAll();
            return Messenger.builder()
                    .code(200)
                    .message("Teams retrieved successfully")
                    .data(teams)
                    .build();
        } catch (Exception e) {
            return Messenger.builder()
                    .code(500)
                    .message("Error retrieving teams: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public Messenger saveAll(List<TeamDTO> teamDTOs) {
        try {
            List<Team> teams = teamDTOs.stream()
                    .map(this::dtoToEntity)
                    .collect(Collectors.toList());
            @SuppressWarnings("null")
            List<Team> saved = teamRepository.saveAll(teams);
            return Messenger.builder()
                    .code(200)
                    .message("Teams saved successfully")
                    .data(saved)
                    .build();
        } catch (Exception e) {
            return Messenger.builder()
                    .code(500)
                    .message("Error saving teams: " + e.getMessage())
                    .build();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Messenger findByKeyword(String keyword) {
        try {
            List<Team> teams = teamRepository.findByKeyword(keyword);
            return Messenger.builder()
                    .code(200)
                    .message("Teams found by keyword: " + keyword)
                    .data(teams)
                    .build();
        } catch (Exception e) {
            return Messenger.builder()
                    .code(500)
                    .message("Error searching teams: " + e.getMessage())
                    .build();
        }
    }

    private Team dtoToEntity(TeamDTO dto) {
        Team team = new Team();
        team.setTeamId(dto.getTeamId());
        team.setRegionName(dto.getRegionName());
        team.setTeamName(dto.getTeamName());
        team.setETeamName(dto.getETeamName());
        team.setOrigYyyy(dto.getOrigYyyy());
        team.setZipCode1(dto.getZipCode1());
        team.setZipCode2(dto.getZipCode2());
        team.setAddress(dto.getAddress());
        team.setDdd(dto.getDdd());
        team.setTel(dto.getTel());
        team.setFax(dto.getFax());
        team.setHomepage(dto.getHomepage());
        team.setOwner(dto.getOwner());
        
        String stadiumId = dto.getStadiumId();
        if (stadiumId != null && !stadiumId.isEmpty()) {
            Stadium stadium = stadiumRepository.findById(stadiumId)
                    .orElse(null);
            team.setStadium(stadium);
        }
        
        return team;
    }

    private void updateEntityFromDto(Team team, TeamDTO dto) {
        if (dto.getRegionName() != null) team.setRegionName(dto.getRegionName());
        if (dto.getTeamName() != null) team.setTeamName(dto.getTeamName());
        if (dto.getETeamName() != null) team.setETeamName(dto.getETeamName());
        if (dto.getOrigYyyy() != null) team.setOrigYyyy(dto.getOrigYyyy());
        if (dto.getZipCode1() != null) team.setZipCode1(dto.getZipCode1());
        if (dto.getZipCode2() != null) team.setZipCode2(dto.getZipCode2());
        if (dto.getAddress() != null) team.setAddress(dto.getAddress());
        if (dto.getDdd() != null) team.setDdd(dto.getDdd());
        if (dto.getTel() != null) team.setTel(dto.getTel());
        if (dto.getFax() != null) team.setFax(dto.getFax());
        if (dto.getHomepage() != null) team.setHomepage(dto.getHomepage());
        if (dto.getOwner() != null) team.setOwner(dto.getOwner());
        
        String stadiumId = dto.getStadiumId();
        if (stadiumId != null && !stadiumId.isEmpty()) {
            Stadium stadium = stadiumRepository.findById(stadiumId)
                    .orElse(null);
            team.setStadium(stadium);
        }
    }
}
