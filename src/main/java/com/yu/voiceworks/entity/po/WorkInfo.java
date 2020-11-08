package com.yu.voiceworks.entity.po;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table
@Entity
public class WorkInfo {
    @Id
    private String workId;

    private String workTitle;

    private String circleId;

    private String ageRatings;

    private String workRelease;
    //系列
    private String seriesId;

    private String workScenario;

    private String imageAuthor;

    private String workFormat;
}
