package org.example.backend_user.repository;

import org.example.backend_user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
     Optional<User> findByUsernameAndPassword(String username, String password);

     Optional<User> findByUsername(String username);
}
