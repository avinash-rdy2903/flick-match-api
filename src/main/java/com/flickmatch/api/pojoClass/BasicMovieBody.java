package com.flickmatch.api.pojoClass;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasicMovieBody {
    private String title;
    private String posterPath;
    private Integer id;
    private String releaseDate;
    private Float voteAverage;
}
