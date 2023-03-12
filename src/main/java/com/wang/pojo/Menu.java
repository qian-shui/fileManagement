package com.wang.pojo;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("vue_menu")
public class Menu {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @Alias("名称")
    private String name;

    @Alias("路径")
    private String path;

    @Alias("图标")
    private String icon;

    @Alias("描述")
    private String description;

    @Alias("父级id")
    private Integer pid;

    @Alias("页面路径")
    private String pagePath;

    @TableField(exist = false)
    private List<Menu> children;


}
