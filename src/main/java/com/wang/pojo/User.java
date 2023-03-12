package com.wang.pojo;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("vue_user")
@ToString
public class User {
    @Alias("ID")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @Alias("用户名")
    private String username;

    @Alias("密码")
    @JsonIgnore
    private String password;

    @Alias("昵称")
    private String nickname;

    @Alias("邮箱")
    private String email;

    @Alias("电话")
    private String phone;

    @Alias("地址")
    private String address;

    @Alias("创建时间")
    private Date createTime;

    @Alias("头像")
    private String avatarUrl;

    @Alias("角色")
    private String role;
}
