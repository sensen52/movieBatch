package com.example.movie.batch;

import com.example.movie.dto.MovieDto;
import com.example.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MovieReader implements ItemReader<MovieDto> {

    private final MovieService movieService;
    private List<MovieDto> movies;
    private int index = 0;

    @Override
    public MovieDto read() throws Exception {
        // 처음 실행 시 영화 정보를 API로부터 가져옴
        if (movies == null) {
            movies = movieService.getMovies();
            if (movies == null || movies.isEmpty()) {
                throw new IllegalStateException("No movies retrieved from the service.");
            }
        }

        // 영화 리스트에서 다음 영화를 읽어옴
        MovieDto nextMovie = null;
        if (index < movies.size()) {
            nextMovie = movies.get(index);
            index++;
        }

        return nextMovie;
    }
}
