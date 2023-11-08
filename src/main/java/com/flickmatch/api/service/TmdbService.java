package com.flickmatch.api.service;

import jakarta.persistence.criteria.CriteriaBuilder;
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


    public ResponseEntity<?> getMoviesList(String listName,String region,int page){
        WebClient.ResponseSpec res = tmdbApiClient.get().uri("/movie/"+listName+"?region="+region+"&page="+page).retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, response -> Mono.error(new RuntimeException("Error with TMDB Api")));
        String response = res.bodyToMono(String.class).block();
        return ResponseEntity.ok(response);
    }
    public ResponseEntity<?> search(String query, Integer page,boolean adult,Integer year,String language){
        WebClient.ResponseSpec res = tmdbApiClient.get().uri("/search/movie?query="+query+"&page="+page+"&adult="+adult+"&primary_release_year="+year+"&language="+language).retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, response -> Mono.error(new RuntimeException("Error with TMDB Api")));
        String response = res.bodyToMono(String.class).block();
        return ResponseEntity.ok(response);
    }
    public ResponseEntity<?> getMovie(String id){
        WebClient.ResponseSpec res = tmdbApiClient.get().uri("/movie/"+id).retrieve()
                .onStatus(HttpStatusCode::is5xxServerError, response -> Mono.error(new RuntimeException("Error with TMDB Api")));
        String response = res.bodyToMono(String.class).block();
        return ResponseEntity.ok(response);
    }
}
