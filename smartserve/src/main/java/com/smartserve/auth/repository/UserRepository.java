package com.smartserve.auth.repository;

import com.smartserve.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
    //to overcome nullpointerexception we have used null pointer
}