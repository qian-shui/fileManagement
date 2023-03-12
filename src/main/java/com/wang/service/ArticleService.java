package com.wang.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wang.pojo.Article;

import java.util.List;

public interface ArticleService {
    List<Article> findAll();

    IPage<Article> findPage(Integer pageNum, Integer pageSize, String name);

    void saveUpdate(Article article);

    void deleteData(Integer id);

    void batchDelete(List<Integer> ids);

    Article findArticleById(Integer id);
}
