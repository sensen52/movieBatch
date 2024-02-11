package com.example.movie.controller;

import com.example.movie.dto.MovieDto;
import com.example.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {
    public final MovieService movieService;

    @GetMapping
    public List<MovieDto> main() throws IOException {
        return movieService.getMovies();
    }
}
