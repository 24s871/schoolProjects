package com.example.planetickets.repository;

import com.example.planetickets.model.TimeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TimeRepo extends JpaRepository<TimeModel,Integer> {
    @Query(value="select id from time",nativeQuery = true)
    String[] ids();
    @Query(value="select flight from time",nativeQuery = true)
    String[] take();
    @Query(value="select id from time where time.flight=flight",nativeQuery = true)
    String getId(String flight);
}
