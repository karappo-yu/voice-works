package com.yu.voiceworks.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yu.voiceworks.entity.po.*;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class VoiceWorkVo {

    private String id;

    private String title;

    private Circle circle;

    private Boolean nsfw;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date release;

    private Integer dlCount;

    private Double price;

    private Integer reviewCount;

    private Integer rateCount;

    private Double rateAverage2dp;

    private List<RateCountDetail> rateCountDetail;

    private List<Rank> rank;

    private List<Tag> tags;

    private List<Cv> vas;
}
