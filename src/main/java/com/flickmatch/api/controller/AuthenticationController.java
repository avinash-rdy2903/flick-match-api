package com.flickmatch.api.controller;

import com.flickmatch.api.pojoClass.*;
import com.flickmatch.api.service.AutheticationService;
import com.flickmatch.api.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AutheticationService autheticationService;
    private final EmailService emailService;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(autheticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) throws Exception{
        return ResponseEntity.ok(autheticationService.autheticate(request));
    }

    @PostMapping("/generate-otp")
    public ResponseEntity<AuthenticationResponse> generateOtp(
            @RequestBody OtpGenerationRequest request
    ){

        try {
            return ResponseEntity.ok(emailService.generateOtp(request.getEmail()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Object> resetPassword(
            @RequestBody ResetPasswordRequest request, @RequestHeader(value="Authorization") String authHeader
    ){
        try {
            return ResponseEntity.ok(autheticationService.resetPassword(authHeader.substring(7), request));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
