package com.example.planetickets.repository;

import com.example.planetickets.model.ClassesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClassRepo extends JpaRepository<ClassesModel,Integer> {
    @Query(value="select id from class",nativeQuery = true)
    String[] ids();
    @Query(value="select economic from class",nativeQuery = true)
    String[] economy();
    @Query(value="select first from class",nativeQuery = true)
    String[] first();
    @Query(value="select second from class",nativeQuery = true)
    String[] second();
    @Query(value="select bussiness from class",nativeQuery = true)
    String[] b();
    @Query(value="select id from class where class.economic=economic and class.first=first and class.second=second and class.bussiness=bussiness",nativeQuery = true)
    String getId(String economic,String first,String second,String bussiness);




}
