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
@Table(name="class")
public class ClassesModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    int economic;
    int first;
    int second;
    int bussiness;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_clasa")
    private List<TicketsModel> bileteList;

    @Override
    public boolean equals(Object o)
    {
        if(this==o) return true;
        if(o==null || getClass()!=o.getClass()) return false;
        ClassesModel that=(ClassesModel) o;
        return Objects.equals(id,that.id) && Objects.equals(economic,that.economic) && Objects.equals(first,that.first)&& Objects.equals(second,that.second)&& Objects.equals(bussiness,that.bussiness);
    }
    @Override
    public int hashCode()
    {
        return Objects.hash(id,economic,bussiness,second,first);
    }

    @Override
    public String toString()
    {
        return "ClassesModel:{"+"id: "+id+" pret economic: "+economic+" pret clasa 2: "+second+" pret clasa 1: "+first+" pret bussiness: "+bussiness;
    }
}
