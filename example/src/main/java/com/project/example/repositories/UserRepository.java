package com.project.example.repositories;

import org.springframework.stereotype.Repository;

import com.project.example.entities.User;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    List<User> findByName(String name);
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndUsername(String email,String username);
}
