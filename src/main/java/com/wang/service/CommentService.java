package com.wang.service;

import com.wang.pojo.Comment;

import java.util.List;

public interface CommentService {
    void saveData(Comment comment);

    List<Comment> findDataByArticleId(Integer articleId);

    void removeDataById(Integer id);
}
