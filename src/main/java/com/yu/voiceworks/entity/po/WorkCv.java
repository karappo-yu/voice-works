package com.yu.voiceworks.entity.po;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class WorkCv {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;


    private String workId;



    private String cvId;




    public WorkCv() {
    }

    public WorkCv(String cvId, String workId) {
        this.cvId = cvId;
        this.workId = workId;
    }
}
