package com.yu.voiceworks.utils;

import java.util.regex.Pattern;

public class RegexUtil {

    private static String MUSIC_FILE_NAME = "^.*\\.(mp3|wav)$";

    /**检查文件名是否是一个音乐文件
     * @param username
     * @return
     */
    public static boolean isMusicFile(String username) {
        return Pattern.matches(MUSIC_FILE_NAME, username);
    }


}
