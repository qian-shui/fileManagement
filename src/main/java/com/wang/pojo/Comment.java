package com.wang.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("vue_comment")
public class Comment {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String content;
    private String time;
    private Integer articleId;
    private Integer userId;
    private Integer replayId;
    private String replayName;

    @TableField(exist = false)
    private String nickname;
    @TableField(exist = false)
    private String avatarUrl;
    @TableField(exist = false)
    private List<Comment> children;
}
