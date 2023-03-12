package com.wang.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wang.common.Constants;
import com.wang.mapper.DictMapper;
import com.wang.pojo.Dict;
import com.wang.pojo.Menu;
import com.wang.pojo.Role;
import com.wang.service.MenuService;
import com.wang.service.RoleService;
import com.wang.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private DictMapper dictMapper;


    @GetMapping
    public Result findAllChildren(@RequestParam(defaultValue = "") String name){
        List<Menu> list = menuService.findAllChildren(name);
        return Result.success("",list);
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name){

        IPage<Menu> page = menuService.findPage(pageNum, pageSize,name);
        return Result.success("查询成功",page);
    }

    @PostMapping
    public Result saveUpdateUser(@RequestBody Menu menu){
        Object idStatus = menu.getId();
        menuService.saveUpdateRole(menu);
        String msg = "";
        if(idStatus!=null){
            msg = "修改成功";
        }else{
            msg = "添加成功";
        }
        return Result.success(msg,null);
    }

    @GetMapping("/delete")
    public Result deleteUser(@RequestParam("id") Integer id){
        menuService.deleteRole(id);
        return Result.success("删除成功",null);
    }

    @PostMapping("/batchDelete")
    public Result batchDeleteUser(@RequestBody List<Integer> ids) {
        menuService.batchDelete(ids);
        return Result.success("删除成功",null);
    }


    @GetMapping("/icons")
    public Result getIcons(){
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type","icon");
        return Result.success("获取图标成功",dictMapper.selectList(queryWrapper));
    }
}
