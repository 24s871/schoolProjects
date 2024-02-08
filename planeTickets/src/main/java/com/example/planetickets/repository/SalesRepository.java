package com.example.planetickets.repository;

import com.example.planetickets.model.SalesModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesRepository extends JpaRepository<SalesModel,Integer> {
}
