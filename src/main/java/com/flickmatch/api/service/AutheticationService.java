package com.flickmatch.api.service;

import com.flickmatch.api.model.Otp;
import com.flickmatch.api.model.Role;
import com.flickmatch.api.model.User;
import com.flickmatch.api.pojoClass.*;
import com.flickmatch.api.repository.OtpRepository;
import com.flickmatch.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AutheticationService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final OtpRepository otpRepository;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        String token = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    public AuthenticationResponse autheticate(AuthenticationRequest request) throws Exception{
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                ()-> new Exception("Incorrect credentials")
        );
        String token = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }
    public AuthenticationResponse resetPassword(String jwt,ResetPasswordRequest request) throws Exception{
        String email = jwtService.extractClaimUserEmail(jwt);
        User user = userRepository.findByEmail(email).orElseThrow(
                ()-> new RuntimeException(String.format("User Input error, {}","Email not found/incorrect"))
        );
        Otp otp = otpRepository.findByEmail(email).orElseThrow(
                ()->new RuntimeException("Please generate a OTP first")
        );
        if(otp.getOtp()!=request.getOtp()){
            throw new RuntimeException(String.format("Provided OTP incorrect"));
        }
        String token = jwtService.generateToken(user);
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        otpRepository.deleteById(otp.getId());
        return AuthenticationResponse.builder()
                .token(token)
                .build();
    }

    public ResponseEntity<BasicUserDetails> getUser(String jwt) {
        User user = userRepository.findByEmail(jwtService.extractClaimUserEmail(jwt))
                .orElseThrow(()-> new RuntimeException("Invalid user Token, close app!!"));
        return ResponseEntity.ok(BasicUserDetails.builder()
                .userId(user.getUserid())
                .email(user.getEmail())
                .lastName(user.getLastName())
                .firstName(user.getFirstName())
                .build()
            );
//        return ResponseEntity.ok(user);
    }

    public ResponseEntity<BasicUserDetails> putUser(BasicUserDetails details) {
        User user = userRepository.getReferenceById(details.getUserId());

        user.setFirstName(details.getFirstName());
        user.setLastName(details.getLastName());
        user.setEmail(details.getEmail());
        userRepository.save(user);
        return ResponseEntity.ok(BasicUserDetails.builder()
                .userId(user.getUserid())
                .email(user.getEmail())
                .lastName(user.getLastName())
                .firstName(user.getFirstName())
                .build()
        );
    }
}
