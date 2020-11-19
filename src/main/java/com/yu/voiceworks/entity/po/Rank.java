package com.yu.voiceworks.entity.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "table_rank")
public class Rank {
//    term": "day",
//            "category": "all",
//            "rank": 12,
//            "rank_date": "2020-10-26"
    @Id
    @JsonIgnore
    @GeneratedValue
    private Integer id;
    @JsonIgnore
    private String workId;

    private String term;

    private String category;

    @Column(name = "ranking")
    private Integer rank;

    private String rankDate;

    public Rank(String term, String category, Integer rank, String rankDate) {
        this.term = term;
        this.category = category;
        this.rank = rank;
        this.rankDate = rankDate;
    }
}
