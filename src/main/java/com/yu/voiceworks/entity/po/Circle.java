package com.yu.voiceworks.entity.po;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table
@Entity
public class Circle {

    @Id
    private String circleId;

    private String circleName;
}
