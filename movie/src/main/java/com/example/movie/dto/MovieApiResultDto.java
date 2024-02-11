package com.example.movie.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class MovieApiResultDto {
    @JsonProperty("Query")
    private String query;
    @JsonProperty("KMAQuery")
    private String kmaQuery;
    @JsonProperty("Data")
    private List<MovieApiDataDto> data;
    @JsonProperty("TotalCount")
    private int totalCount;
}
