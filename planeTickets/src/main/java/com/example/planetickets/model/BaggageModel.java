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
@Table(name="checkedbaggage")
public class BaggageModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    int checkedBaggagePrice;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_checkedbaggage")
    private List<TicketsModel> bileteList;

    @Override
    public boolean equals(Object o)
    {
        if(this==o) return true;
        if(o==null || getClass()!=o.getClass()) return false;
        BaggageModel that=(BaggageModel) o;
        return Objects.equals(id,that.id) && Objects.equals(checkedBaggagePrice,that.checkedBaggagePrice);
    }
    @Override
    public int hashCode()
    {
        return Objects.hash(id,checkedBaggagePrice);
    }

    @Override
    public String toString()
    {
        return "BaggageModel:{"+"id: "+id+" pret bagaj de cala: "+checkedBaggagePrice;
    }
}
