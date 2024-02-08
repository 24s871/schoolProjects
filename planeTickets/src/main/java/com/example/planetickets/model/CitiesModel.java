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
@Getter
@Setter
@Entity
@Table(name = "city")
public class CitiesModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String departureCity;
    String arrivalCity;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_oras")
    private List<TicketsModel> bileteList;

    @Override
    public boolean equals(Object o)
    {
        if(this==o) return true;
        if(o==null || getClass()!=o.getClass()) return false;
        CitiesModel that=(CitiesModel)  o;
        return Objects.equals(id,that.id) && Objects.equals(departureCity,that.departureCity) && Objects.equals(arrivalCity,that.arrivalCity);
    }
    @Override
    public int hashCode()
    {
        return Objects.hash(id,departureCity,arrivalCity);
    }

    @Override
    public String toString()
    {
        return "CitiesModel:{"+"id: "+id+" oras plecare: "+departureCity + " oras sosire: "+arrivalCity;
    }
}
