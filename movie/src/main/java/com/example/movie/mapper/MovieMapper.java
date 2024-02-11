package com.example.movie.mapper;

import com.example.movie.dto.MovieDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MovieMapper {
    void insertMovie(MovieDto movie);
}
