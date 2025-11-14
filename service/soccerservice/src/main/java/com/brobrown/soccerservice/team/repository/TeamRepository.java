package com.brobrown.soccerservice.team.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.brobrown.soccerservice.team.domain.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, String> {
    
    @Query("SELECT t FROM Team t WHERE t.teamName LIKE %:keyword% OR t.eTeamName LIKE %:keyword% OR t.regionName LIKE %:keyword%")
    List<Team> findByKeyword(@Param("keyword") String keyword);
}

