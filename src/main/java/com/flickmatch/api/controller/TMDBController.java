package com.flickmatch.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tmdb")
public class TMDBController {

    @GetMapping
    public ResponseEntity<String> getMovies(){
        return ResponseEntity.ok("All movies");
    }
}
