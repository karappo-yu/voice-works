package com.yu.voiceworks.entity.vo;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class TrackFile {

    private String type;

    private String hash;

    private String title;

    private String workTitle;

    private List<TrackFile> children;
}
