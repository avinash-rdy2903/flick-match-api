package com.flickmatch.api.controller;

import com.flickmatch.api.service.TmdbService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tmdb")
@RequiredArgsConstructor
public class TmdbController {

    private final TmdbService tmdbService;
    @GetMapping(value = "popular", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPopularMovies(){
        return tmdbService.getPopular();
    }
    @GetMapping(value = "top-rated", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTopRatedMovies(){
        return tmdbService.getTopRated();
    }
    @GetMapping(value = "now-playing", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getNowPlayingMovies(){
        return tmdbService.getNowPlaying();
    }
    @GetMapping(value = "upcoming", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUpcomingMovies(){
        return tmdbService.getUpcoming();
    }
}
