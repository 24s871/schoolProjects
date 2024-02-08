package com.example.planetickets.repository;

import com.example.planetickets.model.CompaniesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CompanyRepo extends JpaRepository<CompaniesModel,Integer> {
    @Query(value="select id from company",nativeQuery = true)
    String[] ids();
    @Query(value="select name from company",nativeQuery = true)
    String[] names();
    @Query(value="select id from company where company.name=1",nativeQuery = true)
    String getId(String n);
}
