package com.wang.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.unit.DataUnit;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.common.Constants;
import com.wang.exception.ServiceException;
import com.wang.mapper.ArticleMapper;
import com.wang.pojo.Article;
import com.wang.service.ArticleService;
import com.wang.utils.JWTUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
    @Override
    public List<Article> findAll() {
        try {
            return list();
        }catch (Exception e){
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
    }

    @Override
    public IPage<Article> findPage(Integer pageNum, Integer pageSize, String name) {
        Page<Article> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Article> queryWrapper = new QueryWrapper<>();
        if(!name.isEmpty()) queryWrapper.like("name", name);
        queryWrapper.orderByDesc("id");
        return page(page,queryWrapper);
    }

    @Override
    public void saveUpdate(Article article) {
        try {
            if(article.getId() == null){
                article.setTime(DateUtil.now());
            }
            saveOrUpdate(article);
        }catch (Exception e){
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
    }

    @Override
    public void deleteData(Integer id) {
        try {
            removeById(id);
        }catch (Exception e){
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
    }

    @Override
    public void batchDelete(List<Integer> ids) {
        try {
            removeByIds(ids);
        }catch (Exception e){
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
    }

    @Override
    public Article findArticleById(Integer id) {
        try {
            return getById(id);
        }catch (Exception e){
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
    }
}
