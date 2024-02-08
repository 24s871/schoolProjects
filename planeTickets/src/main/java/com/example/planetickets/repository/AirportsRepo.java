package com.example.planetickets.repository;

import com.example.planetickets.model.AirportsModel;
import com.example.planetickets.model.CompaniesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AirportsRepo extends JpaRepository<AirportsModel,Integer> {

    @Query(value="select id from airport where airport.departure=departure and airport.arrival=arrival",nativeQuery = true)
    String getId(String departure,String arrival);



}
