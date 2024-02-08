package com.example.planetickets.repository;

import com.example.planetickets.model.HoursModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HourRepo extends JpaRepository<HoursModel,Integer> {
    @Query(value="select id from hour",nativeQuery = true)
    String[] ids();
    @Query(value="select arrival from hour",nativeQuery = true)
    String[] arrival();
    @Query(value="select departure from hour",nativeQuery = true)
    String[] departure();

    @Query(value="select id from hour where hour.departure=departure and hour.arrival=arrival",nativeQuery = true)
    String getId(String departure,String arrival);

}
