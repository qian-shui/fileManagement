package com.wang.service;

import com.wang.pojo.User;
import com.wang.vo.UserVo;

public interface LoginService {
    UserVo login(UserVo userVo);

    User register(UserVo userVo);

    User getUserById(String userId);
}
