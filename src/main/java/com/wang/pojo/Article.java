package com.wang.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.soap.Text;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("vue_article")
public class Article {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String name;
    private String content;
    private String user;
    private String time;
}
