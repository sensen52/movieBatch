package com.example.movie.service;

import com.example.movie.dto.MovieApiResultDto;
import com.example.movie.dto.MovieDto;
import com.example.movie.dto.MovieLogDto;
import com.example.movie.mapper.LogMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieService {

    private final LogMapper logMapper;
    ObjectMapper objectMapper = new ObjectMapper(); //응답 직렬화, 요청 역직렬화 할때 사용

    public List<MovieDto> getMovies() throws IOException {

        int index = 0; //영화 index (영화 데이터 순번)
        int limit = 500; // 가져오는 개수
        LocalDate previousDate = getPreviousDate(LocalDate.now()); //현재 날짜의 하루 전 날짜를 가져온다.(get PreviousDate 메서드 실행)
        MovieApiResultDto dto = getMovieApiResultDto(previousDate, index, limit); // 날짜와 페이지 번호를 파라미터로 넘겨서 getmovieApuResultDto 메서드 실행
        List<MovieDto> result = parseJsonToMovieDtoList(dto);  //Json데이터를 MovieDto 리스트로 파싱

        int totalCount = dto.getTotalCount(); //전체 영화 수
        int addedMoviesCount = 0; // 추가된 영화수를 셀 변수

        // 페이지 수만큼 반복해서 영화 목록 가져오기(영화전체 수만큼)
        for (int pageNo = 1; index < totalCount; pageNo++) {
            index = pageNo * limit;
            dto = getMovieApiResultDto(previousDate, index, limit);// 다음페이지의 영화 api결과를 가져온다
            List<MovieDto> movieList = parseJsonToMovieDtoList(dto); //파싱
            result.addAll(movieList); //결과를 movieList에 저장

        }

        log.info("가져온 데이터의 날짜? {}, {}", addedMoviesCount, previousDate);
        System.out.println("가져온 데이터 수 "+addedMoviesCount);
        System.out.println("이전 날짜"+previousDate);

        return result;
    }


    private MovieApiResultDto getMovieApiResultDto(LocalDate previousDate, int index, int limit) throws IOException {


        StringBuilder urlBuilder = new StringBuilder("http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2" +
                "&releaseDts=" + previousDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")) +
                "&releaseDte=" + previousDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")) +
                "&listCount=" + limit +
                "&startCount=" + index);

        String serviceKey = "2021SO7YM94EZ731VF90";
        urlBuilder.append("&" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + serviceKey);

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        System.out.println("urlBuilder.toString(): " + urlBuilder.toString());

        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder response = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            response.append(line);
        }

        //로그 저장
        saveLog(response.toString(), index);
        rd.close();
        conn.disconnect();

        MovieApiResultDto dto = null;
        try {
            dto = objectMapper.readValue(deleteLineSeparator(response.toString()), new TypeReference<>() {
            });
        } catch (Exception e) {
            e.printStackTrace();

        }
        return dto;
    }

    private List<MovieDto> parseJsonToMovieDtoList(MovieApiResultDto dto) throws IOException {
        return dto.getData().get(0).getResult();
    }

    public String deleteLineSeparator(String targetStr) {
        return targetStr.replaceAll("(\r\n|\r|\n|\n\r)", "<br>")
                .replaceAll("(␚)", "!!!!")
                .replaceAll("(\u001A)", "!!!!")
                .replaceAll("\u001A", "!!!!");
    }

    public void saveLog(String response, int startCount) {
        MovieLogDto movieLogDto = new MovieLogDto();
        movieLogDto.setResponse(response);
        movieLogDto.setStartCount(startCount);
        logMapper.insertLog(movieLogDto);
    }

    private static LocalDate getPreviousDate(LocalDate currentDate) {
        return currentDate.minusDays(15);
    }

}

