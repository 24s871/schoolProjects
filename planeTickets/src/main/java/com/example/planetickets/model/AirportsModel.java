package com.example.planetickets.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name="airport")
public class AirportsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String departureAirport;
    String arrivalAirport;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_aeroport")
    private List<TicketsModel> bileteList;

    @Override
    public boolean equals(Object o)
    {
        if(this==o) return true;
        if(o==null || getClass()!=o.getClass()) return false;
        AirportsModel that=(AirportsModel) o;
        return Objects.equals(id,that.id) && Objects.equals(departureAirport,that.departureAirport)&& Objects.equals(arrivalAirport,that.arrivalAirport);
    }
    @Override
    public int hashCode()
    {
        return Objects.hash(id,departureAirport,arrivalAirport);
    }

    @Override
    public String toString()
    {
        return "AirportsModel:{"+"id: "+id+" aeroport plecare: "+departureAirport+" aeroport sosire: "+arrivalAirport;
    }
}
