package com.wang.vo;

import lombok.Data;

@Data
public class PasswordVo {
    private String username;
    private String password;
    private String newPassword;
    private String confirmNewPassword;
}
