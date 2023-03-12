package com.wang.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wang.common.Constants;
import com.wang.pojo.Role;
import com.wang.pojo.User;
import com.wang.service.MenuService;
import com.wang.service.RoleService;
import com.wang.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @GetMapping
    public Result findAllRole(){
        List<Role> list = roleService.findAllRole();
        return Result.success("获取全部role成功",list);
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name){

        IPage<Role> page = roleService.findPage(pageNum, pageSize,name);
        return Result.success("查询成功",page);
    }

    @PostMapping
    public Result saveUpdateUser(@RequestBody Role role){
        Integer idStatus = role.getId();
        boolean result = roleService.saveUpdateRole(role);
        String msg = "";
        if(result){
            if(idStatus!=null){
                msg = "修改成功";
            }else{
                msg = "添加成功";
            }
            return Result.success(msg,null);
        }else{
            return Result.fail(Constants.CODE_600,"添加或修改错误");
        }
    }

    @GetMapping("/delete")
    public Result deleteUser(@RequestParam("id") Integer id){
        roleService.deleteRole(id);
        return Result.success("删除成功",null);
    }

    @PostMapping("/batchDelete")
    public Result batchDeleteUser(@RequestBody List<Integer> ids) {
        roleService.batchDelete(ids);
        return Result.success("删除成功",null);
    }


    @PostMapping("/roleMenu/{roleId}")
    public Result roleMenu(@PathVariable Integer roleId,
                           @RequestBody List<Integer> menuIds){
        List<Integer> menuIds_null = menuService.selectIdByPath();
        List<Integer>  newMenuIds = new ArrayList<>();
        for (Integer menuId : menuIds) {
            if(!menuIds_null.contains(menuId)) newMenuIds.add(menuId);
        }
        roleService.setRoleMenu(roleId,newMenuIds);
        return Result.success("添加菜单成功",null);
    }


    @GetMapping("/getMenuByRole/{roleId}")
    public Result getMenuByRole(@PathVariable Integer roleId){
        List<Integer> list = roleService.getMenuByRole(roleId);
        return Result.success("获取菜单成功",list);
    }
}
