package com.wang.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    //跨域配置
    @Override
    public void addCorsMappings(CorsRegistry registry){
//        registry.addMapping("/**").allowedOrigins("http://localhost:8080");
        registry.addMapping("/**").allowedOrigins("*");
    }
}
