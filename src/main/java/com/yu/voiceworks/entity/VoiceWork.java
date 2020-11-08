package com.yu.voiceworks.entity;

import com.yu.voiceworks.entity.po.*;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

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

    public WorkInfo toWorkInfo(){
        WorkInfo one = new WorkInfo();
        one.setWorkId(id);
        one.setWorkTitle(title);
        one.setAgeRatings(ageRatings);
        one.setWorkFormat(workFormat);
        one.setWorkScenario(scenario);
        one.setImageAuthor(imageAuthor);
        one.setWorkRelease(release);
        if (null!=circle) one.setCircleId(circle.getCircleId());
        if (null!=series)one.setSeriesId(series.getSeriesId());
        return one;
    }
}
