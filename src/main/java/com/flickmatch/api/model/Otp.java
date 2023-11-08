package com.flickmatch.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.boot.autoconfigure.web.WebProperties;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Otp {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(unique = true,nullable = false)
    @NotBlank(message = "email cannot be blank")
    private String email;

    @Range(min = 0, max = 999999, message = "Bad OTP")
    private int otp;
}
