package com.example.movie.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MovieDto {
    @JsonProperty("DOCID")
    private String dOCID;
    private String movieId;
    private String movieSeq;
    private String title;
    private String titleEng;
    private String titleOrg;
    private String titleEtc;
    private String prodYear;
    private Directors directors;
    private Actors actors;
    private String nation;
    private String company;
    private Plots plots;
    private String runtime;
    private String rating;
    private String genre;
    private String kmdbUrl;
    private String type;
    private String use;
    private String episodes;
    private String ratedYn;
    private String repRatDate;
    private String repRlsDate;// 개봉일
    private Ratings ratings;
    private String keywords;
    private String posters;
    private String stlls;
    private Staffs staffs;
    private Vods vods;
    private String openThtr;
    private List<Stat> stat;
    private String screenArea;
    private String screenCnt;
    private String salesAcc;
    private String audiAcc;
    private String statSouce;
    private String statDate;
    private String themeSong;
    private String soundtrack;

    @JsonProperty("fLocation")
    private String fLocation;
    @JsonProperty("Awards1")
    private String awards1;
    @JsonProperty("Awards2")
    private String awards2;
    private String regDate;
    private String modDate;
    @JsonProperty("Codes")
    private Codes codes;
    @JsonProperty("CommCodes")
    private CommCodes commCodes;
    @JsonProperty("ALIAS")
    private String aLIAS;
}

@Getter
@Setter
class Actor {
    private String actorNm;
    private String actorEnNm;
    private String actorId;
}

@Getter
@Setter
class Actors {
    private List<Actor> actor;
}

@Getter
@Setter
class Code {
    @JsonProperty("CodeNm")
    private String codeNm;
    @JsonProperty("CodeNo")
    private String codeNo;
}

@Getter
@Setter
class Codes {
    @JsonProperty("Code")
    private List<Code> code;
}

@Getter
@Setter
class CommCode {
    @JsonProperty("CommCodeNm")
    private String commCodeNm;
    @JsonProperty("CommCodeNo")
    private String commCodeNo;
    @JsonProperty("CodeNm")
    private String codeNm;
    @JsonProperty("CodeNo")
    private String codeNo;
}

@Getter
@Setter
class CommCodes {
    @JsonProperty("CommCode")
    private List<CommCode> commCode;
}

@Getter
@Setter
class Director {
    private String directorNm;
    private String directorEnNm;
    private String directorId;
}

@Getter
@Setter
class Directors {
    @JsonProperty("director")
    private List<Director> directors;
}

@Getter
@Setter
class Plot {
    private String plotLang;
    private String plotText;
}

@Getter
@Setter
class Plots {
    private List<Plot> plot;
}

@Getter
@Setter
class Rating {
    private String ratingMain;
    private String ratingDate;
    private String ratingNo;
    private String ratingGrade;
    private String releaseDate;
    private String runtime;
}

@Getter
@Setter
class Ratings {
    private List<Rating> rating;
}

@Getter
@Setter
class Staff {
    private String staffNm;
    private String staffEnNm;
    private String staffRoleGroup;
    private String staffRole;
    private String staffEtc;
    private String staffId;
}

@Getter
@Setter
class Staffs {
    private List<Staff> staff;
}

@Getter
@Setter
class Stat {
    private String screenArea;
    private String screenCnt;
    private String salesAcc;
    private String audiAcc;
    private String statSouce;
    private String statDate;
}

@Getter
@Setter
class Vod {
    private String vodClass;
    private String vodUrl;
}

@Getter
@Setter
class Vods {
    private List<Vod> vod;
}

