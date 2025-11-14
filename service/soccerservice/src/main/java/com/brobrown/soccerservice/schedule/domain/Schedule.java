package com.brobrown.soccerservice.schedule.domain;

import com.brobrown.soccerservice.stadium.domain.Stadium;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "schedule")
@Data
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String scheDate;

    @ManyToOne
    @JoinColumn(name = "stadium_id")
    private Stadium stadium;

    private String gubun;

    private String hometeamId;

    private String awayteamId;

    private Integer homeScore;

    private Integer awayScore;

}
