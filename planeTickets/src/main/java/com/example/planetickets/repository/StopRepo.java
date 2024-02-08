package com.example.planetickets.repository;

import com.example.planetickets.model.StopoverModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StopRepo extends JpaRepository<StopoverModel,Integer> {
    @Query(value="select id from stopover",nativeQuery = true)
    String[] ids();
    @Query(value="select stopover from stopover",nativeQuery = true)
    String[] citi();

    @Query(value="select id from stopover where stopover.stopover=stopover",nativeQuery = true)
    String getId(String stopover);
}
