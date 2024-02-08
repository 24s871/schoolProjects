package com.example.planetickets.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SalesDto {
    private String clientId;
    private String ticketId;
    private String total;
    private String numar_card;
    private String cvc;
    private String expira;
    private String nume;
}
