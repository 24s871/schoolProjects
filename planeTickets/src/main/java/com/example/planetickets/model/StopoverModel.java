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
@Table(name="stopover")
public class StopoverModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String stopoverCity;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_escala")
    private List<TicketsModel> bileteList;

    @Override
    public boolean equals(Object o)
    {
        if(this==o) return true;
        if(o==null || getClass()!=o.getClass()) return false;
        StopoverModel that=(StopoverModel) o;
        return Objects.equals(id,that.id) && Objects.equals(stopoverCity,that.stopoverCity);
    }
    @Override
    public int hashCode()
    {
        return Objects.hash(id,stopoverCity);
    }

    @Override
    public String toString()
    {
        return "StopoverModel:{"+"id: "+id+" oras escala: "+stopoverCity;
    }
}
