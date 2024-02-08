package com.example.planetickets.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Setter
@Getter
@Table(name = "ticket")
public class TicketsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    int seats;//d
    int id_companie;//d
    int id_aeroport;//d
    int id_data;
    int id_ora;
    int id_oras;
    int id_clasa;
    int id_escala;
    int id_durata;
    int id_checkedbaggage;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private List<SalesModel> salesList;


}
