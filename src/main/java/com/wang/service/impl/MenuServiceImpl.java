package com.wang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.common.Constants;
import com.wang.exception.ServiceException;
import com.wang.mapper.MenuMapper;
import com.wang.pojo.Menu;
import com.wang.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public IPage<Menu> findPage(Integer pageNum, Integer pageSize, String name) {
        Page<Menu> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        if(!name.isEmpty()) queryWrapper.like("name", name);
        queryWrapper.orderByDesc("id");
        return page(page,queryWrapper);
    }

    @Override
    public void saveUpdateRole(Menu menu) {
        try {
            if(menu.getPid() == null) menu.setPid(0);
            saveOrUpdate(menu);
        }catch (Exception e){
            throw new ServiceException(Constants.CODE_500,"sql语句执行异常");
        }
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


    @Override
    public List<Menu> findAllChildren(String name) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        if(!name.isEmpty()) queryWrapper.like("name", name);
        List<Menu> list = list(queryWrapper);
        List<Menu> collect = list.stream().filter(menu -> menu.getPid() == 0).collect(Collectors.toList());
        for (Menu menu : collect) {
            menu.setChildren(list.stream().filter(m -> menu.getId().equals(m.getPid())).collect(Collectors.toList()));
        }
        return collect;
    }

    @Override
    public List<Integer> selectIdByPath() {
        List<Integer> list = menuMapper.selectIdByPath();
        return list;
    }
}
