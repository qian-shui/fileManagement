package com.wang.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wang.pojo.Role;
import com.wang.pojo.User;

import java.util.List;

public interface RoleService {
    IPage<Role> findPage(Integer pageNum, Integer pageSize, String name);

    boolean saveUpdateRole(Role role);

    void deleteRole(Integer id);

    void batchDelete(List<Integer> ids);

    void setRoleMenu(Integer roleId, List<Integer> menuIds);

    List<Integer> getMenuByRole(Integer roleId);

    List<Role> findAllRole();
}
