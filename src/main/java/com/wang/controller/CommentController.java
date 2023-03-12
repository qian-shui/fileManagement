package com.wang.controller;

import cn.hutool.core.date.DateUtil;
import com.wang.config.AuthAccess;
import com.wang.pojo.Comment;
import com.wang.service.CommentService;
import com.wang.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/save")
    public Result save(@RequestBody Comment comment){
        comment.setTime(DateUtil.now());
        commentService.saveData(comment);
        return Result.success("评论成功",null);
    }

    @AuthAccess
    @GetMapping("/tree/{articleId}")
    public Result findTree(@PathVariable Integer articleId){
        List<Comment> list = commentService.findDataByArticleId(articleId);
        return Result.success("评论数据获取成功",list);
    }

    @GetMapping("/delete/{id}")
    public Result removeById(@PathVariable Integer id){
        commentService.removeDataById(id);
        return Result.success("评论删除成功",null);
    }
}
