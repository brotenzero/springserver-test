package com.brobrown.soccerservice.stadium.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.brobrown.soccerservice.common.Messenger;
import com.brobrown.soccerservice.stadium.domain.Stadium;
import com.brobrown.soccerservice.stadium.domain.StadiumDTO;
import com.brobrown.soccerservice.stadium.repository.StadiumRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class StadiumServiceImpl implements StadiumService {

    private final StadiumRepository stadiumRepository;

    @Override
    public Messenger save(StadiumDTO stadiumDTO) {
        try {
            Stadium stadium = dtoToEntity(stadiumDTO);
            @SuppressWarnings("null")
            Stadium saved = stadiumRepository.save(stadium);
            return Messenger.builder()
                    .code(200)
                    .message("Stadium saved successfully")
                    .data(saved)
                    .build();
        } catch (Exception e) {
            return Messenger.builder()
                    .code(500)
                    .message("Error saving stadium: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public Messenger update(StadiumDTO stadiumDTO) {
        try {
            String stadiumUk = stadiumDTO.getStadiumUk();
            if (stadiumUk == null || stadiumUk.isEmpty()) {
                throw new RuntimeException("Stadium ID is required");
            }
            Stadium stadium = stadiumRepository.findById(stadiumUk)
                    .orElseThrow(() -> new RuntimeException("Stadium not found"));
            updateEntityFromDto(stadium, stadiumDTO);
            @SuppressWarnings("null")
            Stadium updated = stadiumRepository.save(stadium);
            return Messenger.builder()
                    .code(200)
                    .message("Stadium updated successfully")
                    .data(updated)
                    .build();
        } catch (Exception e) {
            return Messenger.builder()
                    .code(500)
                    .message("Error updating stadium: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public Messenger delete(String id) {
        try {
            if (id == null || id.isEmpty()) {
                throw new RuntimeException("Stadium ID is required");
            }
            stadiumRepository.deleteById(id);
            return Messenger.builder()
                    .code(200)
                    .message("Stadium deleted successfully")
                    .build();
        } catch (Exception e) {
            return Messenger.builder()
                    .code(500)
                    .message("Error deleting stadium: " + e.getMessage())
                    .build();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Messenger findById(String id) {
        try {
            if (id == null || id.isEmpty()) {
                throw new RuntimeException("Stadium ID is required");
            }
            Stadium stadium = stadiumRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Stadium not found"));
            return Messenger.builder()
                    .code(200)
                    .message("Stadium found")
                    .data(stadium)
                    .build();
        } catch (Exception e) {
            return Messenger.builder()
                    .code(500)
                    .message("Error finding stadium: " + e.getMessage())
                    .build();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Messenger findAll() {
        try {
            List<Stadium> stadiums = stadiumRepository.findAll();
            return Messenger.builder()
                    .code(200)
                    .message("Stadiums retrieved successfully")
                    .data(stadiums)
                    .build();
        } catch (Exception e) {
            return Messenger.builder()
                    .code(500)
                    .message("Error retrieving stadiums: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public Messenger saveAll(List<StadiumDTO> stadiumDTOs) {
        try {
            List<Stadium> stadiums = stadiumDTOs.stream()
                    .map(this::dtoToEntity)
                    .collect(Collectors.toList());
            @SuppressWarnings("null")
            List<Stadium> saved = stadiumRepository.saveAll(stadiums);
            return Messenger.builder()
                    .code(200)
                    .message("Stadiums saved successfully")
                    .data(saved)
                    .build();
        } catch (Exception e) {
            return Messenger.builder()
                    .code(500)
                    .message("Error saving stadiums: " + e.getMessage())
                    .build();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Messenger findByKeyword(String keyword) {
        try {
            List<Stadium> stadiums = stadiumRepository.findByKeyword(keyword);
            return Messenger.builder()
                    .code(200)
                    .message("Stadiums found by keyword: " + keyword)
                    .data(stadiums)
                    .build();
        } catch (Exception e) {
            return Messenger.builder()
                    .code(500)
                    .message("Error searching stadiums: " + e.getMessage())
                    .build();
        }
    }

    private Stadium dtoToEntity(StadiumDTO dto) {
        Stadium stadium = new Stadium();
        stadium.setStadiumId(dto.getStadiumUk());
        stadium.setStadiumName(dto.getStadiumName());
        stadium.setHometeamId(dto.getHometeamId());
        stadium.setSeatCount(dto.getSeatCount());
        stadium.setAddress(dto.getAddress());
        stadium.setDdd(dto.getDdd());
        stadium.setTel(dto.getTel());
        return stadium;
    }

    private void updateEntityFromDto(Stadium stadium, StadiumDTO dto) {
        if (dto.getStadiumName() != null) stadium.setStadiumName(dto.getStadiumName());
        if (dto.getHometeamId() != null) stadium.setHometeamId(dto.getHometeamId());
        if (dto.getSeatCount() != null) stadium.setSeatCount(dto.getSeatCount());
        if (dto.getAddress() != null) stadium.setAddress(dto.getAddress());
        if (dto.getDdd() != null) stadium.setDdd(dto.getDdd());
        if (dto.getTel() != null) stadium.setTel(dto.getTel());
    }
}
