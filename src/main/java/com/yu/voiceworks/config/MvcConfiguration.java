package com.yu.voiceworks.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UrlPathHelper;

@Configuration
@EnableConfigurationProperties(SSYKConfigProperties.class)
public class MvcConfiguration implements WebMvcConfigurer {

    private String basePath;

    private String imgPath;

    private final static String PREFIX = "file:";

    private SSYKConfigProperties properties;

    @Autowired
    public void setProperties(SSYKConfigProperties properties) {
        this.properties = properties;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/mp3/**").addResourceLocations(PREFIX +properties.getBasePath()+"/");
        registry.addResourceHandler("/img/**").addResourceLocations(PREFIX+ properties.getCoverPath()+"/");

    }
//    tomcat默认是不支持转义，需要手动设置一下转化，
//    搜索tomcat的设置可以找到，但是这个是springboot，
//    有内置的tomcat，但是在yml中找不到相关的配置。
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        UrlPathHelper urlPathHelper = new UrlPathHelper();
        urlPathHelper.setUrlDecode(false);
        configurer.setUrlPathHelper(urlPathHelper);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")

                .allowedOrigins("*")

                .allowCredentials(true)

                .allowedMethods("GET", "POST", "DELETE", "PUT")

                .maxAge(3600);

    }
}
