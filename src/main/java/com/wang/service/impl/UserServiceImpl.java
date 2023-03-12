package com.wang.service.impl;

import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.common.Constants;
import com.wang.mapper.UserMapper;
import com.wang.pojo.User;
import com.wang.service.UserService;
import com.wang.exception.ServiceException;
import com.wang.vo.PasswordVo;
import com.wang.vo.UserVo;
import org.springframework.stereotype.Service;
import org.springframework.beans.BeanUtils;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {

//    @Autowired
//    private UserMapper userMapper;
    //使用mybatis-plus不再需要注入userMapper

    @Override
    public List<User> findAllUser() {
        return list();
//        return userMapper.findAllUser();
    }


    @Override
    public boolean saveUpdateUser(User user) {
        return saveOrUpdate(user);
//        if(user.getId() == null){
//            return userMapper.saveUser(user);
//        }else{
//            return userMapper.updateUser(user);
//        }
    }

    @Override
    public boolean deleteUser(Integer id) {
        return removeById(id);
//        return userMapper.deleteUser(id);
    }


//    @Override
//    public List<User> selectPage(Integer pageNum, Integer pageSize,String username) {
//        return userMapper.selectPage(pageNum,pageSize,username);
//    }
//
//    @Override
//    public Integer selectTotal(String username) {
//        return userMapper.selectTotal(username);
//    }

    //使用mybatisplus分页
    @Override
    public IPage<User> findPage(Integer pageNum, Integer pageSize, String username,String phone,String address) {
        Page<User> page = new Page<>(pageNum, pageSize);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(!username.isEmpty()) queryWrapper.like("username", username);
        if(!phone.isEmpty()) queryWrapper.like("phone", phone);
        if(!address.isEmpty()) queryWrapper.like("address", address);
        queryWrapper.orderByDesc("id");
        return page(page,queryWrapper);
    }

    @Override
    public boolean batchDeleteUser(List<Integer> ids) {
        return removeByIds(ids);
    }

    @Override
    public void exportData(HttpServletResponse response) throws Exception {
        List<User> allUser = list();
        //自定义execl表头
        ExcelWriter writer = ExcelUtil.getWriter(true);
//        writer.addHeaderAlias("username","用户名");
//        writer.addHeaderAlias("password","密码");
//        writer.addHeaderAlias("nickname","昵称");
//        writer.addHeaderAlias("email","邮箱");
//        writer.addHeaderAlias("phone","电话");
//        writer.addHeaderAlias("address","地址");
//        writer.addHeaderAlias("createTime","创建时间");
//        writer.addHeaderAlias("avatarUrl","头像");

        //一次性写出list数据到execl
        writer.write(allUser,true);

        //设置浏览器响应格式
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = URLEncoder.encode("用户信息", "UTF-8");
        response.setHeader("Content-Disposition","attachment;fileName="+fileName+".xlsx");

        //获取到输出流，把writer刷新到输出流去
        ServletOutputStream out = response.getOutputStream();
        writer.flush(out,true);
        out.close();
        writer.close();
    }

    @Override
    public boolean saveBatchUser(List<User> users) {
        return saveBatch(users);
    }


    @Override
    public User findUserByUsername(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        return getOne(queryWrapper);
    }

    @Override
    public List<User> findUserByRole(String role) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role",role);
        return list(queryWrapper);
    }


}
