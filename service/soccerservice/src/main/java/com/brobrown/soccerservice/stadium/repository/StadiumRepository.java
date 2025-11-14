package com.brobrown.soccerservice.stadium.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.brobrown.soccerservice.stadium.domain.Stadium;

@Repository
public interface StadiumRepository extends JpaRepository<Stadium, String> {
    
    @Query("SELECT s FROM Stadium s WHERE s.stadiumName LIKE %:keyword% OR s.address LIKE %:keyword%")
    List<Stadium> findByKeyword(@Param("keyword") String keyword);
}

