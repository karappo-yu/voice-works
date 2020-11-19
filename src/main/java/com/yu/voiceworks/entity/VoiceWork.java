package com.yu.voiceworks.entity;

import com.yu.voiceworks.entity.po.*;
import com.yu.voiceworks.utils.RegexUtil;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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


    public WorkInfo toWorkInfo() {
        //2020年11月17日 16時
        String datePatter = "yyyy年MM月dd日 hh時";
        WorkInfo one = new WorkInfo();
        one.setWorkId(id);
        one.setWorkTitle(title);
        one.setAgeRatings(ageRatings);
        one.setWorkFormat(workFormat);
        one.setWorkScenario(scenario);
        one.setImageAuthor(imageAuthor);
        try {
            one.setWorkRelease(RegexUtil.releaseStr2Date(release));
        } catch (ParseException e) {
            one.setWorkRelease(null);
        }
        if (null != circle) one.setCircleId(circle.getCircleId());
        if (null != series) one.setSeriesId(series.getSeriesId());
        return one;
    }
}
