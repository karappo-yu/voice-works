package com.yu.voiceworks.entity.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table
public class RateCountDetail {
//    rate_count_detail": [{
//            "review_point": 1,
//            "count": 0,
//            "ratio": 0
    @Id
    @JsonIgnore
    @GeneratedValue
    private Integer id;

    @JsonIgnore
    private String workId;

    private Integer reviewPoint;

    private Integer count;

    private Integer ratio;

    public RateCountDetail() {
    }

    public RateCountDetail(Integer reviewPoint, Integer count, Integer ratio) {
        this.reviewPoint = reviewPoint;
        this.count = count;
        this.ratio = ratio;
    }
}
