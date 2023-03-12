package com.wang.config;

import java.lang.annotation.*;

/*
* 自定义注解，当接口带上此注解，就可以放行，不经过拦截器
* */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthAccess {
    //也可以自定义一些东西
}
