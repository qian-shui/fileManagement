package com.wang.pojo;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("vue_role")
public class Role {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @Alias("名称")
    private String name;

    @Alias("描述")
    private String description;

    @Alias("唯一标识")
    private String flag;
}
