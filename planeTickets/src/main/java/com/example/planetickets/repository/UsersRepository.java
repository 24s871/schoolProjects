package com.example.planetickets.repository;

import com.example.planetickets.model.UsersModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<UsersModel,Integer> {
    Optional<UsersModel> findByNameAndPassword(String name,String password);
    @Query(value="select * from users",nativeQuery = true)
    List<UsersModel> findAllUsers();
    @Modifying
    @Query(value = "delete from users where id = :id",nativeQuery = true)
    void deleteUsersByFirstName(@Param("id") int id);
}
