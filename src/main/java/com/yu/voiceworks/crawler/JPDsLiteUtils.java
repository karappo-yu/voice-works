package com.yu.voiceworks.crawler;

import org.apache.commons.lang3.StringUtils;

public class JPDsLiteUtils {
    public static  String COOKIE_LOCALE = "locale=ja-jp";
    public static  String AGE_RATINGS = "年齢指定";
    public static  String GENRE = "ジャンル";
    public static  String VA = "声優";

    public static  String SCENARIO = "シナリオ";

    public static  String RELEASE = "販売日";
    public static  String SERIES = "シリーズ名";
    public static  String WORK_FORMAT = "作品形式";
    public static  String IMAGE_AUTHOR = "イラスト";

    public static  String JP = "ja-jp";
    
    public static  String TW= "zh-tw";

    public static String CH = "zh-cn";

    public static void setLang(String lang) {
        if (StringUtils.equals(lang,JP)){
            return;
        }
        if (StringUtils.equals(lang,TW)){
            COOKIE_LOCALE = "locale=zh-tw";
            AGE_RATINGS = "年齡指定";
            GENRE = "分類";
            VA = "聲優";
            SCENARIO = "劇本";
            RELEASE = "販賣日";
            SERIES = "系列名";
            WORK_FORMAT = "作品形式";
            IMAGE_AUTHOR = "插畫";
        }
        if (StringUtils.equals(lang,CH)){
            COOKIE_LOCALE = "locale=zh-tw";
            AGE_RATINGS = "年龄指定";
            GENRE = "分类";
            VA = "声优";
            SCENARIO = "剧情";
            RELEASE = "贩卖日";
            SERIES = "系列名";
            WORK_FORMAT = "作品类型";
            IMAGE_AUTHOR = "插画";
        }
    }
}
