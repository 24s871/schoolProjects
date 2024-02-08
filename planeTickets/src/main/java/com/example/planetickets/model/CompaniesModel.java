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
@Table(name="company")
public class CompaniesModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String name;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_companie")
    private List<TicketsModel> bileteList;

    @Override
    public boolean equals(Object o)
    {
        if(this==o) return true;
        if(o==null || getClass()!=o.getClass()) return false;
        CompaniesModel that=(CompaniesModel) o;
        return Objects.equals(id,that.id) && Objects.equals(name,that.name);
    }
    @Override
    public int hashCode()
    {
        return Objects.hash(id,name);
    }

    @Override
    public String toString()
    {
        return "CompaniesModel:{"+"id: "+id+"name: "+name;
    }
}
