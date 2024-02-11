package com.example.movie.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MovieLogDto {
    private Date createTime;
    private String response;

}
