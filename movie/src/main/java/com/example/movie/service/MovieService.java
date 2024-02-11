package com.example.movie.service;

import com.example.movie.dto.MovieApiResultDto;
import com.example.movie.dto.MovieDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

    public List<MovieDto>  getMovies() throws IOException {
        StringBuilder urlBuilder = new StringBuilder("http://api.koreafilm.or.kr/openapi-data2/wisenut/search_api/search_json2.jsp?collection=kmdb_new2&nation=" + URLEncoder.encode("대한민국", "UTF-8"));

        String serviceKey = "2021SO7YM94EZ731VF90";
        urlBuilder.append("&" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + serviceKey);

        // 요청 파라미터 추가
        urlBuilder.append("&" + URLEncoder.encode("val001", "UTF-8") + "=" + URLEncoder.encode("2023", "UTF-8"));
        urlBuilder.append("&" + URLEncoder.encode("val002", "UTF-8") + "=" + URLEncoder.encode("01", "UTF-8"));

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

        rd.close();
        conn.disconnect();

        ObjectMapper objectMapper = new ObjectMapper();
        MovieApiResultDto dto = objectMapper.readValue(response.toString(), new TypeReference<>() {});

        return parseJsonToMovieDtoList(dto);
    }

    private List<MovieDto> parseJsonToMovieDtoList(MovieApiResultDto dto) throws IOException {
        return dto.getData().get(0).getResult();
    }

}


