package com.yu.voiceworks.entity.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table
@Entity
@DynamicInsert
public class WorkInfo {
    @Id
    private String workId;

    private String workTitle;

    private String circleId;

    private String ageRatings;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date workRelease;
    //系列
    private String seriesId;

    private String workScenario;

    private String imageAuthor;

    private String workFormat;

    private Date createTime;
}
