package com.example.planetickets.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Information {


    private String departure;
    private String arrival;
    private String dateDeparture;
    private String clasa;
    private String passangers;
    private String direct;
    private String stopover;
    private String duration;
    private String transit;
    private String airline;
    private String numberOfLuggages;
    private String pay;


}
