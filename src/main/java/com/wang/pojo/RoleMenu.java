package com.wang.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("vue_role_menu")
@AllArgsConstructor
@NoArgsConstructor
public class RoleMenu {
    private Integer roleId;
    private Integer menuId;
}
