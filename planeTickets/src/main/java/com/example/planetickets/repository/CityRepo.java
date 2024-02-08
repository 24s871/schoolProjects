package com.example.planetickets.repository;

import com.example.planetickets.model.CitiesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CityRepo extends JpaRepository<CitiesModel,Integer> {
    @Query(value="select id from city",nativeQuery = true)
    String[] ids();
    @Query(value="select departure from city",nativeQuery = true)
    String[] departure();
    @Query(value="select arrival from city",nativeQuery = true)
    String[] arrival();

    @Query(value="select id from city where city.departure=departure and city.arrival=arrival",nativeQuery = true)
    String getId(String departure,String arrival);

}
