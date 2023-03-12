package com.wang.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Quarter;
import com.wang.pojo.User;
import com.wang.service.UserService;
import com.wang.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/echarts")
public class EchartsController {

    @Autowired
    private UserService userService;

    @GetMapping("/countUserByTime")
    public Result countUserByTime(){
        List<User> allUser = userService.findAllUser();
        int a = 0;
        int b = 0;
        int c = 0;
        int d = 0;
        for (User user : allUser) {
            Date createTime = user.getCreateTime();
            Quarter quarter = DateUtil.quarterEnum(createTime);
            switch (quarter){
                case Q1: a += 1; break;
                case Q2: b += 1; break;
                case Q3: c += 1; break;
                case Q4: d += 1; break;
                default:break;
            }
        }
        return Result.success("折线数据获取成功",CollUtil.newArrayList(a,b,c,d));
    }
}
