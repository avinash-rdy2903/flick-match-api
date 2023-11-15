package com.flickmatch.api.controller;

import com.flickmatch.api.model.User;
import com.flickmatch.api.pojoClass.*;
import com.flickmatch.api.service.AutheticationService;
import com.flickmatch.api.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new AuthenticationResponse(e.getMessage()));
        }
    }
    @GetMapping(value = "/user",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BasicUserDetails> getUser(@RequestHeader(value="Authorization") String authHeader){
        String jwt = authHeader.substring(7);
        return autheticationService.getUser(jwt);
    }

    @PutMapping(value = "/user",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BasicUserDetails> putUser(@RequestBody BasicUserDetails request){
        return autheticationService.putUser(request);
    }
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
            @RequestBody ResetPasswordRequest request, @RequestHeader(value="Authorization") String authHeader
    ){
        try {
            System.out.println(authHeader);
            return ResponseEntity.ok(autheticationService.resetPassword(authHeader.substring(7), request));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
