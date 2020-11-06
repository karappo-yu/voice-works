package com.yu.voiceworks.entity;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@ToString
public class VoiceWork {
    private String id;

    private String title;
/*
社团
 */
    private Circle circle;

    private String ageRatings;

    private String release;
//系列
    private Series series;

    private String scenario;

    private String imageAuthor;

    private Set<Tag> tags;

    private Set<Cv> cvs;

    private String workFormat = "ボイス・ASMR";
}
