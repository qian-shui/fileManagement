package com.wang.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wang.pojo.User;
import com.wang.vo.PasswordVo;
import com.wang.vo.UserVo;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

public interface UserService {
    List<User> findAllUser();

    boolean saveUpdateUser(User user);

    boolean deleteUser(Integer id);

//    List<User> selectPage(Integer pageNum, Integer pageSize,String username);
//
//    Integer selectTotal(String username);
    IPage<User> findPage(Integer pageNum, Integer pageSize, String username,String phone,String address);

    boolean batchDeleteUser(List<Integer> ids);

    void exportData(HttpServletResponse response) throws Exception;

    boolean saveBatchUser(List<User> users);

    User findUserByUsername(String username);


    List<User> findUserByRole(String role);


}
