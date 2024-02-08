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
@Entity
@Setter
@Getter
@Table(name = "time")
public class TimeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    int flightTime;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_durata")
    private List<TicketsModel> bileteList;

    @Override
    public boolean equals(Object o)
    {
        if(this==o) return true;
        if(o==null || getClass()!=o.getClass()) return false;
        TimeModel that=(TimeModel) o;
        return Objects.equals(id,that.id) && Objects.equals(flightTime,that.flightTime);
    }
    @Override
    public int hashCode()
    {
        return Objects.hash(id,flightTime);
    }

    @Override
    public String toString()
    {
        return "TimeModel:{"+"id: "+id+" durata zbor: "+flightTime;
    }
}
