package com.example.movie.mapper;

import com.example.movie.dto.MovieLogDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogMapper {

    void insertLog(MovieLogDto movieLogDto);
}
