package com.example.movie.batch;

import com.example.movie.dto.MovieDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MovieItemProcessor implements ItemProcessor<MovieDto,MovieDto> {
    //필요한 정보 추출 및 가공 작업
    //주어진 입력을 가져와 처리하고 수정된 영화 정보를 변환하는 역할을 한다

    //openapi라서 받은 그대로 반환
    @Override
    public MovieDto process(MovieDto movie) throws Exception{

        return movie;
    }
}
