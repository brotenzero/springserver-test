package com.brobrown.soccerservice.stadium.domain;

import java.util.List;

import com.brobrown.soccerservice.schedule.domain.Schedule;
import com.brobrown.soccerservice.team.domain.Team;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "stadium")
@Data
public class Stadium {

    @Id
    private String stadiumId;

    private String stadiumName;

    private String hometeamId;

    private Integer seatCount;

    private String address;

    private String ddd;

    private String tel;

    @OneToOne(mappedBy = "stadium")
    private Team team;

    @OneToMany(mappedBy = "stadium")
    private List<Schedule> schedules;

}
