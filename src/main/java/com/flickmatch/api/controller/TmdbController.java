package com.flickmatch.api.controller;

import com.flickmatch.api.service.TmdbService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping(value="search",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> search(@RequestParam(value="query")String query,
                                    @RequestParam(value = "page",required = false,defaultValue = "1") Integer page,
                                    @RequestParam(value = "adult",required = false,defaultValue = "false") boolean adult,
                                    @RequestParam(value = "year",required = false,defaultValue = "0") Integer year,
                                    @RequestParam(value = "language",required = false,defaultValue = "en") String language){
        return tmdbService.search(query,page,adult,year,language);
    }
    @GetMapping(value = "movie", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMovie(@RequestParam(value = "id",defaultValue = "US",required = false) String id){
        return tmdbService.getMovie(id);
    }
}
