package com.example.movie.batch;

import com.example.movie.dto.MovieDto;
import com.example.movie.mapper.MovieMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MovieWriter implements ItemWriter<MovieDto> {

    //처리된 영화정보를 데이터 베이스에 쓰게 된다.(저장)
    //ItemProcessor에서 가공된 데이터를 받아서 최종적으로 어떻게 처리할 지 정의하는 부분

    private final MovieMapper movieMapper;

    @Override
    public void write(Chunk<? extends MovieDto> chunk) throws Exception {
        // Chunk 단위로 받은 영화 정보를 데이터베이스에 저장, 각 영화 정보는 Chunk 안에 있는 MovieDto 객체에 담겨있다.
        // 가공된 영화 정보를 순회하면서 DB에 저장
        for (MovieDto movie : chunk.getItems()) {

            movieMapper.insertMovie(movie);// MovieMapper를 사용하여 현재 영화 정보를 데이터베이스에 저장
        }
    }
}
