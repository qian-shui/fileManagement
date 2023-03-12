package com.wang.controller;

import cn.hutool.core.util.StrUtil;
import com.wang.common.Constants;
import com.wang.pojo.User;
import com.wang.service.LoginService;
import com.wang.service.UserService;
import com.wang.vo.Result;
import com.wang.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public Result login(@RequestBody UserVo userVo){
        String username = userVo.getUsername();
        String password = userVo.getPassword();
        if(StrUtil.isBlank(username) || StrUtil.isBlank(password)){
            return Result.fail(Constants.CODE_400,"用户名或密码参数不足！");
        }
        UserVo login = loginService.login(userVo);
        login.setPassword("");
        return Result.success("登录成功",login);
    }


    @PostMapping("/register")
    public Result register(@RequestBody UserVo userVo){
        String username = userVo.getUsername();
        String password = userVo.getPassword();
        if(StrUtil.isBlank(username) || StrUtil.isBlank(password)){
            return Result.fail(Constants.CODE_400,"用户名或密码参数不足！");
        }
        User user = loginService.register(userVo);
        return Result.success("注册成功",user);
    }




}
