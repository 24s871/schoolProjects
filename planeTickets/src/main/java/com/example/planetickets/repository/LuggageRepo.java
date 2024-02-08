package com.example.planetickets.repository;

import com.example.planetickets.model.BaggageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LuggageRepo extends JpaRepository<BaggageModel,Integer> {
    @Query(value="select id from checkedbaggage",nativeQuery = true)
    String[] ids();
    @Query(value="select price from checkedbaggage",nativeQuery = true)
    String[] prices();
    @Query(value="select id from checkedbaggage where checkedbaggage.price=price",nativeQuery = true)
    String getId(String price);
}
