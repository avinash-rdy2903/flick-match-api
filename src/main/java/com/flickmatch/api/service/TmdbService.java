package com.flickmatch.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TmdbService {

    private final WebClient tmdbApiClient;


    public ResponseEntity<?> getPopular(){
        WebClient.ResponseSpec res = tmdbApiClient.get().uri("/movie/popular").retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, response -> Mono.error(new RuntimeException("Error with TMDB Api")));
        String response = res.bodyToMono(String.class).block();
        return ResponseEntity.ok(response);
    }
    public ResponseEntity<?> getGenre() {
        WebClient.ResponseSpec res = tmdbApiClient.get().uri("/genre/movie/list").retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, response -> Mono.error(new RuntimeException("Error with TMDB Api")));
        String response = res.bodyToMono(String.class).block();
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> getTopRated() {
        WebClient.ResponseSpec res = tmdbApiClient.get().uri("/movie/top_rated").retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, response -> Mono.error(new RuntimeException("Error with TMDB Api")));
        String response = res.bodyToMono(String.class).block();
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> getNowPlaying() {
        WebClient.ResponseSpec res = tmdbApiClient.get().uri("/movie/now_playing").retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, response -> Mono.error(new RuntimeException("Error with TMDB Api")));
        String response = res.bodyToMono(String.class).block();
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> getUpcoming() {
        WebClient.ResponseSpec res = tmdbApiClient.get().uri("/movie/upcoming").retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, response -> Mono.error(new RuntimeException("Error with TMDB Api")));
        String response = res.bodyToMono(String.class).block();
        return ResponseEntity.ok(response);
    }
}
