package com.wang.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wang.pojo.Menu;
import com.wang.pojo.Role;

import java.util.List;

public interface MenuService {
    IPage<Menu> findPage(Integer pageNum, Integer pageSize, String name);

    void saveUpdateRole(Menu menu);

    void deleteRole(Integer id);

    void batchDelete(List<Integer> ids);

    List<Menu> findAllChildren(String name);

    List<Integer> selectIdByPath();
}
