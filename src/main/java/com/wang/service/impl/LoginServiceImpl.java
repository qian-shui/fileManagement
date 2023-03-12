package com.wang.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.common.Constants;
import com.wang.exception.ServiceException;
import com.wang.mapper.RoleMapper;
import com.wang.mapper.RoleMenuMapper;
import com.wang.mapper.UserMapper;
import com.wang.pojo.Menu;
import com.wang.pojo.Role;
import com.wang.pojo.User;
import com.wang.service.LoginService;
import com.wang.service.MenuService;
import com.wang.utils.JWTUtils;
import com.wang.vo.UserVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginServiceImpl  extends ServiceImpl<UserMapper, User> implements LoginService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Autowired
    private MenuService menuService;

    @Override
    public UserVo login(UserVo userVo) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",userVo.getUsername());
        queryWrapper.eq("password",userVo.getPassword());
        User one;
        try {
            one = getOne(queryWrapper);
        }catch (Exception e){
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
        if(one != null){
            BeanUtils.copyProperties(one,userVo);
            String token = JWTUtils.getToken(one.getId().toString());
            userVo.setToken(token);
            String role = one.getRole();

            Integer roleId = roleMapper.selectByFlag(role);
            //根据角色id获取到菜单id信息
            List<Integer> menuIds = roleMenuMapper.selectByRoleId(roleId);
            List<Integer> menuIds_a = menuService.selectIdByPath();
            for (Integer integer : menuIds_a) {
                menuIds.add(integer);
            }

            //查出系统所有的菜单
            List<Menu> allChildren = menuService.findAllChildren("");
            //根据菜单id获取到菜单信息
            List<Menu> roleMenu = new ArrayList<>();
            for (Menu menu : allChildren) {
                if(menuIds.contains(menu.getId())){
                    menu.getChildren().removeIf(child -> !menuIds.contains(child.getId()));
                    if(menuIds_a.contains(menu.getId())){
                        if(menu.getChildren().size() != 0) roleMenu.add(menu);
                    }else{
                        roleMenu.add(menu);
                    }
                }
            }
            userVo.setId(one.getId());
            userVo.setRole(role);
            userVo.setMenus(roleMenu);
            return userVo;
        }else{
            throw new ServiceException(Constants.CODE_600,"用户名或密码错误");
        }
    }

    @Override
    public User register(UserVo userVo) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",userVo.getUsername());
        User one;
        try {
            one = getOne(queryWrapper);
        }catch (Exception e){
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
        if(one == null){
            User user = new User();
            BeanUtils.copyProperties(userVo,user);
            user.setAvatarUrl("https://cube.elemecdn.com/9/c2/f0ee8a3c7c9638a54940382568c9dpng.png");
            save(user);
            return user;
        }else{
            throw new ServiceException(Constants.CODE_600,"用户已存在");
        }
    }

    @Override
    public User getUserById(String userId) {
        return getById(userId);
    }

}
