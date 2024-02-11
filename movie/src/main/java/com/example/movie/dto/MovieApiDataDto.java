package com.example.movie.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MovieApiDataDto {
    @JsonProperty("CollName")
    private String collName;
    @JsonProperty("Count")
    private int count;
    @JsonProperty("Result")
    private List<MovieDto> result;
    @JsonProperty("TotalCount")
    private int totalCount;

}
