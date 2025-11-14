package com.brobrown.soccerservice.schedule.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.brobrown.soccerservice.schedule.domain.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    
    @Query("SELECT s FROM Schedule s WHERE s.scheDate LIKE %:keyword% OR s.hometeamId LIKE %:keyword% OR s.awayteamId LIKE %:keyword%")
    List<Schedule> findByKeyword(@Param("keyword") String keyword);
}
