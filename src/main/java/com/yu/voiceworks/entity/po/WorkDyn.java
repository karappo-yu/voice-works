package com.yu.voiceworks.entity.po;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table
@Entity
public class WorkDyn {
    @Id
    private String workId ;

    private Integer price; // 价格

    private Integer dlCount; // 售出数

    private Integer rateAverage; // 平均评价

    private Double rateAverage2Dp; // 平均评价

    private Integer rateCount; // 评价数量

    private Integer reviewCount;  // 评论数量

}
