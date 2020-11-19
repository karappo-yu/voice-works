package com.yu.voiceworks.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "ssyk")
public class SSYKConfigProperties {
    //音乐文件夹存放根目录
    private String basePath = "F:/voice";

    //封面存放目录
    private String coverPath = "F:/voice/img";

    //是否使用代理
    private boolean isUseProxy = false;


    //代理地址
    private String proxyURL = "localhost";

    //代理端口
    private int proxyPort = 10809;

    //超时时间
    private int timeout = 8000;

    //扫描深度
    private int scanDepth = 2;


}
