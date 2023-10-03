package com.Boot.boot.repository;


import com.Boot.boot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

//Saves an entity and flushes changes instantly.
//Params:
//entity – entity to be saved. Must not be null.
//Returns:
//the saved entity


    User findByEmail(String email);

}