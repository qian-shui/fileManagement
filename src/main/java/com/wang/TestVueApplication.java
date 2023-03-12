package com.wang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@MapperScan("com.wang.mapper")
@SpringBootApplication
//@EnableCaching
public class TestVueApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestVueApplication.class, args);
    }

}
