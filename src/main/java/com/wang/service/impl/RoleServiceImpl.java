package com.wang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.common.Constants;
import com.wang.exception.ServiceException;
import com.wang.mapper.MenuMapper;
import com.wang.mapper.RoleMapper;
import com.wang.mapper.RoleMenuMapper;
import com.wang.pojo.Role;
import com.wang.pojo.RoleMenu;
import com.wang.pojo.User;
import com.wang.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public IPage<Role> findPage(Integer pageNum, Integer pageSize, String name) {
        Page<Role> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        if(!name.isEmpty()) queryWrapper.like("name", name);
        queryWrapper.orderByDesc("id");
        return page(page,queryWrapper);
    }

    @Override
    public boolean saveUpdateRole(Role role) {
        return saveOrUpdate(role);
    }

    @Override
    public void deleteRole(Integer id) {
        try {
            removeById(id);
        }catch (Exception e){
            throw new ServiceException(Constants.CODE_500,"sql语句执行异常");
        }
    }

    @Override
    public void batchDelete(List<Integer> ids) {
        try {
            removeByIds(ids);
        }catch (Exception e){
            throw new ServiceException(Constants.CODE_500,"sql语句执行异常");
        }
    }


    @Transactional
    @Override
    public void setRoleMenu(Integer roleId, List<Integer> menuIds) {
        QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id",roleId);
        roleMenuMapper.delete(queryWrapper);
        for (Integer menuId : menuIds) {
            roleMenuMapper.insert(new RoleMenu(roleId,menuId));
        }
    }

    @Override
    public List<Integer> getMenuByRole(Integer roleId) {
        List<Integer> list = roleMenuMapper.selectByRoleId(roleId);

        return list;
    }

    @Override
    public List<Role> findAllRole() {
        return list();
    }
}
