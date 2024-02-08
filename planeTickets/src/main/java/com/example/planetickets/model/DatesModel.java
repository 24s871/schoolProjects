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
@Table(name="date")
public class DatesModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String departureDate;
    String arrivalDate;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_data")
    private List<TicketsModel> bileteList;

    @Override
    public boolean equals(Object o)
    {
        if(this==o) return true;
        if(o==null || getClass()!=o.getClass()) return false;
        DatesModel that=(DatesModel) o;
        return Objects.equals(id,that.id) && Objects.equals(departureDate,that.departureDate) && Objects.equals(arrivalDate,that.arrivalDate);
    }
    @Override
    public int hashCode()
    {
        return Objects.hash(id,departureDate,arrivalDate);
    }

    @Override
    public String toString()
    {
        return "DatesModel:{"+"id: "+id+" data plecare: "+departureDate + " data sosire: "+arrivalDate;
    }
}
