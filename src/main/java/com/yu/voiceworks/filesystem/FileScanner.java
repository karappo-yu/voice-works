package com.yu.voiceworks.filesystem;

import com.yu.voiceworks.entity.po.WorkDir;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileScanner {

    private static Pattern PATTERN = Pattern.compile("[rR][jJ]\\d{6}");

    public static String BASE_PATH = "f://voice";

    public static int SCAN_DEPTH = 2;



    public static Set<WorkDir> scan(File basePath) {
        if (!basePath.exists()) {
            basePath.mkdir();
        }

        Set<WorkDir> dirs = new HashSet<>();
        if (basePath.isDirectory()) {
            File[] files = basePath.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    String name = file.getName();
                    Matcher matcher = PATTERN.matcher(name);
                    if (matcher.find()) {  //查找是否含有RJ+6数字开头的的文件夹名，找到了就加入set
                        String id = matcher.group().substring(2, 8);
                        String path = file.getPath();
                        dirs.add(new WorkDir(id, path));
                    }else {//没有找到就递归继续查找该目录,并把结果加入Set
                        if (SCAN_DEPTH>0){
                            SCAN_DEPTH--;
                            Set<WorkDir> result = scan(file);
                            dirs.addAll(result);
                        }
                    }
                }
            }
        }
        return dirs;
    }
    public static Set<WorkDir> scanBasePath(){

        return scan(new File(BASE_PATH));
    }
}
