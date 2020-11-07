package com.yu.voiceworks.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Rank {
//    term": "day",
//            "category": "all",
//            "rank": 12,
//            "rank_date": "2020-10-26"
    private String term;

    private String category;

    private Integer rank;

    private String rankDate;
}
