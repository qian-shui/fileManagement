package com.wang.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wang.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {


    //mybatis实现user的增删改查
//    List<User> findAllUser();

//    Integer saveUser(User user);
//
//    Integer updateUser(User user);

//    Integer deleteUser(Integer id);

//    List<User> selectPage(Integer pageNum, Integer pageSize,String username);
//
//    @Select("select count(*) from vue_user where username like concat('%',#{username},'%')")
//    Integer selectTotal(String username);
}
