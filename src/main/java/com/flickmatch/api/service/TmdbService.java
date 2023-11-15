package com.flickmatch.api.service;

import com.flickmatch.api.model.Movie;
import com.flickmatch.api.model.User;
import com.flickmatch.api.pojoClass.BasicMovieBody;
import com.flickmatch.api.repository.MovieRepository;
import com.flickmatch.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TmdbService {

    private final WebClient tmdbApiClient;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

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

    private User extractUser(String jwtToken){
        String email =  jwtService.extractClaimUserEmail(jwtToken);
        return userRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("User not found, please close the app and relaunch it."));
    }

    private Movie putMovie(BasicMovieBody body){
        if(!movieRepository.existsById(body.getId())){
            Movie movie = Movie.builder()
                    .id(body.getId())
                    .title(body.getTitle())
                    .posterPath(body.getPosterPath())
                    .releaseDate(body.getReleaseDate())
                    .voteAverage(body.getVoteAverage())
                    .build();
            movieRepository.save(movie);
        }
        return movieRepository.getReferenceById(body.getId());
    }
    public ResponseEntity<?> putFavoriteMovie(String jwtToken, BasicMovieBody body){
        User user = extractUser(jwtToken);
        Movie movie = putMovie(body);
        Set<Movie> favouriteList = user.getFavoriteListMovies();
        favouriteList.add(movie);
        user.setFavoriteListMovies(favouriteList);
        userRepository.save(user);
        return ResponseEntity.ok("Successfully saved!");
    }

    public ResponseEntity<?> deleteFavoriteMovie(String jwtToken, Integer id) {
        User user = extractUser(jwtToken);
        Set<Movie> favoriteList = user.getFavoriteListMovies().stream()
                .filter(movie -> !Objects.equals(movie.getId(), id))
                .collect(Collectors.toSet());
        user.setFavoriteListMovies(favoriteList);
        userRepository.save(user);
        return ResponseEntity.ok("Successfully removed!");
    }

    public ResponseEntity<?> getFavoriteMovies(String jwtToken) {
        User user = extractUser(jwtToken);
        return ResponseEntity.ok(user.getFavoriteListMovies());
    }

    public ResponseEntity<?> putWatchlistMovie(String jwtToken, BasicMovieBody body) {
        User user = extractUser(jwtToken);
        Movie movie = putMovie(body);
        Set<Movie> watchlistMovies = user.getWatchlistMovies();
        watchlistMovies.add(movie);
        user.setWatchlistMovies(watchlistMovies);
        userRepository.save(user);
        return ResponseEntity.ok("Successfully saved!");
    }

    public ResponseEntity<?> getWatchlistMovies(String jwtToken) {
        User user = extractUser(jwtToken);
        return ResponseEntity.ok(user.getWatchlistMovies());
    }

    public ResponseEntity<?> deleteWatchlistMovie(String jwtToken, Integer id) {
        User user = extractUser(jwtToken);
        Set<Movie> watchlist = user.getWatchlistMovies().stream()
                .filter(movie -> !Objects.equals(movie.getId(), id))
                .collect(Collectors.toSet());
        user.setWatchlistMovies(watchlist);
        userRepository.save(user);
        return ResponseEntity.ok("Successfully removed!");
    }
}
