package com.flickmatch.api.repository;

import com.flickmatch.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {
        Optional<User> findByEmail(String email);
}