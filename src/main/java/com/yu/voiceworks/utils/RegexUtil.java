package com.yu.voiceworks.utils;

import org.springframework.http.HttpStatus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtil {

    public static final String MUSIC_FILE_NAME_PATTERN = "^.*\\.(mp3|wav)$";

    public static final String RJ_CODE_PATTERN = "^\\d{6}$";

    public static final String RELEASE_DATE_PATTERN = "^\\d{4}年\\d{1,2}月\\d{1,2}日";

    public static final String DATE_PATTER = "yyyy年MM月dd日";

    /**检查文件名是否是一个音乐文件
     *
     * @param username
     * @return
     */
    public static boolean isMusicFile(String username) {
        return Pattern.matches(MUSIC_FILE_NAME_PATTERN, username);
    }

    /**
     * 将发售日期的字符串转换为日期格式
     * @param release
     * @return
     * @throws ParseException
     */
    public static Date releaseStr2Date(String release) throws ParseException {
        String str = "";
        Pattern pattern = Pattern.compile(RELEASE_DATE_PATTERN);
        Matcher matcher = pattern.matcher(release.trim());
        if (matcher.find()){
            str = matcher.group(0);
        }
        return new SimpleDateFormat(DATE_PATTER).parse(str);
    }
}
