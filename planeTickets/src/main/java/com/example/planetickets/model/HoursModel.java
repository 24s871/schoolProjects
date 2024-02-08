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
@Table(name="hour")
public class HoursModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String departure;
    String arrival;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_ora")
    private List<TicketsModel> bileteList;

    @Override
    public boolean equals(Object o)
    {
        if(this==o) return true;
        if(o==null || getClass()!=o.getClass()) return false;
        HoursModel that=(HoursModel)  o;
        return Objects.equals(id,that.id) && Objects.equals(departure,that.departure) && Objects.equals(arrival,that.arrival);
    }
    @Override
    public int hashCode()
    {
        return Objects.hash(id,arrival,departure);
    }

    @Override
    public String toString()
    {
        return "UsersModel:{"+"id: "+id+" data plecare: "+departure + " data sosire: "+arrival;
    }
}
