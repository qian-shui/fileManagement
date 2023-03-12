package com.wang.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wang.config.AuthAccess;
import com.wang.pojo.Article;
import com.wang.service.ArticleService;
import com.wang.vo.Result;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    private static String dataName = "article";

    @GetMapping
    public Result findAll(){
        List<Article> list = articleService.findAll();
        return Result.success("获取全部数据"+dataName+"成功",list);
    }

    @AuthAccess
    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name){

        IPage<Article> page = articleService.findPage(pageNum, pageSize,name);
        return Result.success("查询成功",page);
    }

    @PostMapping
    public Result saveUpdate(@RequestBody Article article){
        Integer idStatus = article.getId();
        articleService.saveUpdate(article);
        String msg = "";
        if(idStatus!=null){
            msg = "修改成功";
        }else{
            msg = "添加成功";
        }
        return Result.success(msg,null);
    }

    @GetMapping("/delete")
    public Result deleteData(@RequestParam("id") Integer id){
        articleService.deleteData(id);
        return Result.success("删除成功",null);
    }

    @PostMapping("/batchDelete")
    public Result batchDelete(@RequestBody List<Integer> ids) {
        articleService.batchDelete(ids);
        return Result.success("删除成功",null);
    }

    @AuthAccess
    @GetMapping("/findArticleById/{id}")
    public Result findArticleById(@PathVariable Integer id){
        Article article = articleService.findArticleById(id);
        return Result.success("根据id查询成功",article);
    }


}
