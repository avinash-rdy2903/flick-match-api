package com.flickmatch.api.controller;

import com.flickmatch.api.pojoClass.BasicMovieBody;
import com.flickmatch.api.service.TmdbService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tmdb")
@RequiredArgsConstructor
public class TmdbController {

    private final TmdbService tmdbService;
    @GetMapping(value = "popular", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPopularMovies(@RequestParam(value = "region",defaultValue = "US",required = false) String region,@RequestParam(value = "page",required = false,defaultValue = "1") Integer page){
        return tmdbService.getMoviesList("popular",region,page);
    }
    @GetMapping(value = "top-rated", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTopRatedMovies(@RequestParam(value = "region",defaultValue = "US",required = false) String region,@RequestParam(value = "page",required = false,defaultValue = "1") Integer page){
        return tmdbService.getMoviesList("top_rated",region,page);
    }
    @GetMapping(value = "now-playing", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getNowPlayingMovies(@RequestParam(value = "region",defaultValue = "US",required = false) String region,@RequestParam(value = "page",required = false,defaultValue = "1") Integer page){
        return tmdbService.getMoviesList("now_playing",region,page);
    }
    @GetMapping(value = "upcoming", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUpcomingMovies(@RequestParam(value = "region",defaultValue = "US",required = false) String region,@RequestParam(value = "page",required = false,defaultValue = "1") Integer page){
        return tmdbService.getMoviesList("upcoming",region,page);
    }
    @GetMapping(value="movie/search",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> search(@RequestParam(value="query")String query,
                                    @RequestParam(value = "page",required = false,defaultValue = "1") Integer page,
                                    @RequestParam(value = "adult",required = false,defaultValue = "false") boolean adult,
                                    @RequestParam(value = "year",required = false,defaultValue = "0") Integer year,
                                    @RequestParam(value = "language",required = false,defaultValue = "en") String language){
        return tmdbService.search(query,page,adult,year,language);
    }
    @GetMapping(value = "movie/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMovie(@PathVariable(value = "id") String id){
        return tmdbService.getMovie(id);
    }

    @PutMapping(value = "movie/favorite")
    public ResponseEntity<?> putFavoriteMovie(
            @RequestBody BasicMovieBody body, @RequestHeader(value="Authorization") String authHeader
            ){
        System.out.println("Put mapping");
        String jwtToken = authHeader.substring(7);
        return tmdbService.putFavoriteMovie(jwtToken,body);
    }

    @GetMapping(value = "movie/favorite")
    public ResponseEntity<?> getFavoriteMovies( @RequestHeader(value="Authorization") String authHeader ){
        String jwtToken = authHeader.substring(7);
        return tmdbService.getFavoriteMovies(jwtToken);
    }

    @DeleteMapping(value = "movie/favorite/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteFavoriteMovie(@PathVariable Integer id,@RequestHeader(value="Authorization") String authHeader
    ) {
        String jwtToken = authHeader.substring(7);
        return tmdbService.deleteFavoriteMovie(jwtToken, id);
    }

    @PutMapping(value = "movie/watchlist")
    public ResponseEntity<?> putWatchlistMovie(
            @RequestBody BasicMovieBody body, @RequestHeader(value="Authorization") String authHeader
    ){
        String jwtToken = authHeader.substring(7);
        return tmdbService.putWatchlistMovie(jwtToken,body);
    }

    @GetMapping(value = "movie/watchlist")
    public ResponseEntity<?> getWatchlistMovies( @RequestHeader(value="Authorization") String authHeader ){
        String jwtToken = authHeader.substring(7);
        return tmdbService.getWatchlistMovies(jwtToken);
    }

    @DeleteMapping(value = "movie/watchlist/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteWatchlistMovie(@PathVariable Integer id,@RequestHeader(value="Authorization") String authHeader
    ) {
        String jwtToken = authHeader.substring(7);
        return tmdbService.deleteWatchlistMovie(jwtToken, id);
    }
}
