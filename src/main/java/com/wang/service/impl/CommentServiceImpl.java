package com.wang.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.common.Constants;
import com.wang.exception.ServiceException;
import com.wang.mapper.CommentMapper;
import com.wang.pojo.Comment;
import com.wang.service.CommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Resource
    private CommentMapper commentMapper;

    @Override
    public void saveData(Comment comment) {
        try {
            save(comment);
        }catch (Exception e){
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
    }

    @Override
    public List<Comment> findDataByArticleId(Integer articleId) {
        //查询该文章所有的评论和回复
        List<Comment> allComments = commentMapper.findDataByArticleId(articleId);
        //查询该文章所有的评论
        List<Comment> comments = allComments.stream().filter(comment -> comment.getReplayId()==null).collect(Collectors.toList());
        for (Comment comment : comments) {
            //查询该评论下所有的回复
            comment.setChildren(allComments.stream().filter(c -> comment.getId().equals(c.getReplayId())).collect(Collectors.toList()));
        }
        return comments;
    }

    @Override
    public void removeDataById(Integer id) {
        removeById(id);
    }
}
