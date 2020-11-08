package com.yu.voiceworks.entity.po;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Table
@Entity
@Data
public class WorkTag {
    @Id
    private String tagId;


    private String workId;


    public WorkTag() {
    }

    public WorkTag(String tagId, String workId) {
        this.tagId = tagId;
        this.workId = workId;
    }
}
