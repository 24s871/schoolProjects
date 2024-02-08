package com.example.planetickets.repository;

import com.example.planetickets.model.DatesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DateRepo extends JpaRepository<DatesModel,Integer> {
    @Query(value="select id from date",nativeQuery = true)
    String[] ids();
    @Query(value="select arrival from date",nativeQuery = true)
    String[] arrival();
    @Query(value="select departure from date",nativeQuery = true)
    String[] departure();

    @Query(value="select id from date where date.departure=departure and date.arrival=arrival",nativeQuery = true)
    String getId(String departure,String arrival);


}
