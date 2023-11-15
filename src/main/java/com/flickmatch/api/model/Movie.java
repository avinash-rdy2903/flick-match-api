package com.flickmatch.api.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Movie {
    @Id
    private Integer id;

    @NotNull(message = "Title cannot be null")
    private String title;

    @NotNull(message = "Poster path cannot be null")
    private String posterPath;

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private String releaseDate;

    private Float voteAverage;

//    @ManyToMany(mappedBy = "favoriteListMovies")
//    private List<User> favoriteListUsers;
//
//    @ManyToMany(mappedBy = "watchListMovies")
//    private List<User> watchListUsers;

}
