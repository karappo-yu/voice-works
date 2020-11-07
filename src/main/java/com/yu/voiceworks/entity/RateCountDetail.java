package com.yu.voiceworks.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RateCountDetail {
//    rate_count_detail": [{
//            "review_point": 1,
//            "count": 0,
//            "ratio": 0
    private Integer reviewPoint;

    private Integer count;

    private Integer ratio;
}
