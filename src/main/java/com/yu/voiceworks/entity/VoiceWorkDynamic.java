package com.yu.voiceworks.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class VoiceWorkDynamic {
/*    price: data.price,
    dl_count: data.dl_count,
    rate_average: data.rate_average,
    rate_average_2dp: data.rate_average_2dp,
    rate_count: data.rate_count,
    rate_count_detail: data.rate_count_detail,
    review_count: data.review_count,
    rank: data.rank */

    private String id ;

    private Integer price; // 价格

    private Integer dlCount; // 售出数

    private Integer rateAverage; // 平均评价

    private Double rateAverage2Dp; // 平均评价

    private Integer rateCount; // 评价数量

    private List<RateCountDetail> rateCountDetail; // 评价分布明细

    private Integer reviewCount;  // 评论数量

    private List<Rank> rank; // 历史销售成绩


}
