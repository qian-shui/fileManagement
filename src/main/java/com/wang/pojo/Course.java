package com.wang.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("vue_course")
public class Course {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String name;
    private String score;
    private String times;
    private boolean state;
    private Integer teacherId;
    @TableField(exist = false)
    private String teacher;


}
