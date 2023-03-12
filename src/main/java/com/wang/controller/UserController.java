package com.wang.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wang.common.Constants;
import com.wang.pojo.User;
import com.wang.service.UserService;
import com.wang.vo.PasswordVo;
import com.wang.vo.Result;
import com.wang.vo.UserVo;
import org.apache.poi.hssf.record.DVALRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public Result findAllUser(){
        List<User> allUser = userService.findAllUser();
        return Result.success("查询所有用户",allUser);
    }

    @GetMapping("/username/{username}")
    public Result findUserByUsername(@PathVariable String username){
        User user = userService.findUserByUsername(username);
        return Result.success("根据用户名查询用户",user);
    }

    @PostMapping
    public Result saveUpdateUser(@RequestBody User user){
        Object idStatus = user.getId();
        boolean result = userService.saveUpdateUser(user);
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

//    @DeleteMapping("/{id}")
    @GetMapping("/deleteUser")
    public Result deleteUser(@RequestParam("id") Integer id){
        boolean i = userService.deleteUser(id);
        if(i){
            return Result.success("删除成功",null);
        }else{
            return Result.success("删除失败",null);
        }
    }

    @PostMapping("/batchDeleteUser")
    public boolean batchDeleteUser(@RequestBody List<Integer> ids) {
        return userService.batchDeleteUser(ids);
    }



//    @GetMapping("/page")
//    public Map<String,Object> findPage(@RequestParam Integer pageNum,
//                                       @RequestParam Integer pageSize,
//                                       @RequestParam String username){
//        pageNum = (pageNum-1)*pageSize;
//        List<User> data = userService.selectPage(pageNum,pageSize,username);
//        Integer userTotal = userService.selectTotal(username);
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("total",userTotal);
//        map.put("data",data);
//        return map;
//    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                                  @RequestParam Integer pageSize,
                                  @RequestParam(defaultValue = "") String username,
                                  @RequestParam(defaultValue = "") String phone,
                                  @RequestParam(defaultValue = "") String address){

        IPage<User> page = userService.findPage(pageNum, pageSize, username, phone, address);
        return Result.success("查询成功",page);
    }


    //导出数据库数据生成xlxs文件
    @GetMapping("/export")
    public void exportData(HttpServletResponse response) throws Exception {
        userService.exportData(response);
    }


    //从前端导入数据到数据库
    @PostMapping("/import")
    public boolean importData(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<User> users = reader.readAll(User.class);
        return userService.saveBatchUser(users);
    }


    @PostMapping("/updatePassword")
    public Result updatePassword(@RequestBody PasswordVo passwordVo){

        User user = userService.findUserByUsername(passwordVo.getUsername());
        if(!user.getPassword().equals(passwordVo.getPassword())){
            return Result.fail(Constants.CODE_400,"原密码输入错误");
        }
        user.setPassword(passwordVo.getNewPassword());
        userService.saveUpdateUser(user);
        return Result.success("密码修改成功",null);
    }


    @GetMapping("/role/{role}")
    public Result findUserByRole(@PathVariable String role){
        List<User> list = userService.findUserByRole(role);
        return Result.success("查询"+role+"成功",list);
    }

}
