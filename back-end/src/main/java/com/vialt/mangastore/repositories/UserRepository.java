package com.vialt.mangastore.repositories;

import com.vialt.mangastore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByFirstName(String firstName);
    List<User> findByLastName(String lastName);
    List<User> findByEmail(String email);
    boolean existsByEmail(String email);

}