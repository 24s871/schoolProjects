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
@Table(name="users")
public class UsersModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String name;
    String email;
    String password;
    String role;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private List<SalesModel> salesList;

    @Override
    public boolean equals(Object o)
    {
        if(this==o) return true;
        if(o==null || getClass()!=o.getClass()) return false;
        UsersModel that=(UsersModel) o;
        return Objects.equals(id,that.id) && Objects.equals(name,that.name) && Objects.equals(email,that.email)&& Objects.equals(password,that.password)&& Objects.equals(role,that.role);
    }
    @Override
    public int hashCode()
    {
        return Objects.hash(id,name,email,password,role);
    }

    @Override
    public String toString()
    {
        return "UsersModel:{"+"id: "+id+" name: "+name+" email: "+email+" role: "+role;
    }

}
