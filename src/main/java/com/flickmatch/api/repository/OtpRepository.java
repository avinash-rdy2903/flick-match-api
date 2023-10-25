package com.flickmatch.api.repository;


import com.flickmatch.api.model.Otp;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp,String> {
    public Optional<Otp> findByEmail(String email);

}
