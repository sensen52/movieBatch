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
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieService {

    private final LogMapper logMapper;
    ObjectMapper objectMapper = new ObjectMapper();

    public List<MovieDto> getMovies() throws IOException {
        int index = 0;
        MovieApiResultDto dto = getMovieApiResultDto(index);
        int totalCount = dto.getTotalCount();
        List<MovieDto> result = parseJsonToMovieDtoList(dto);

        for (int pageNo = 1; index < totalCount; pageNo++) {
            index = pageNo * 500;
            dto = getMovieApiResultDto(index);
            List<MovieDto> movieList = parseJsonToMovieDtoList(dto);
            result.addAll(movieList);
        }
        return result;
    }

    private MovieApiResultDto getMovieApiResultDto(int index) throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2" +
                "&releaseDts=20000101" +
                "&listCount=500" +
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

}

