package com.example.planetickets.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TicketInfo {
    private int id;
    private int seats;
    private String company;
    private String departureCity;
    private String arrivalCity;
    private String departureAirport;
    private String arrivalAirport;
    private String departureDate;
    private String arrivalDate;
    private String departureHour;
    private String arrivalHour;
    private int economicPrice;
    private int firstPrice;
    private int secondPrice;
    private int bussinessPrice;
    private String stopoverCity;
    private int flightTime;
    private int checkBaggagePrice;
    private String action;
}
