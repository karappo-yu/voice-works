package com.yu.voiceworks.entity.po;

import lombok.Data;

import javax.persistence.*;

@Table
@Entity
@Data
public class WorkTag {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private String tagId;


    private String workId;


    public WorkTag() {
    }

    public WorkTag(String tagId, String workId) {
        this.tagId = tagId;
        this.workId = workId;
    }
}
