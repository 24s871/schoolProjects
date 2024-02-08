package com.example.planetickets.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InfoUser {
    int id;
    String name;
    String email;
    String password;
    String role;
    String action;
}
