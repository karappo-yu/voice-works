package com.yu.voiceworks.entity.po;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
@Data
public class WorkCv {

    @Id
    private String cvId;


    private String workId;


    public WorkCv() {
    }

    public WorkCv(String cvId, String workId) {
        this.cvId = cvId;
        this.workId = workId;
    }
}
