package com.wang.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wang.common.Constants;
import com.wang.exception.ServiceException;
import com.wang.mapper.StudentCourseMapper;
import com.wang.pojo.Course;
import com.wang.pojo.Role;
import com.wang.pojo.StudentCourse;
import com.wang.service.CourseService;
import com.wang.service.MenuService;
import com.wang.service.RoleService;
import com.wang.service.UserService;
import com.wang.vo.Result;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private UserService userService;

    @Autowired
    private StudentCourseMapper studentCourseMapper;

    private static String dataName = "course";

    @GetMapping
    public Result findAll(){
        List<Course> list = courseService.findAll();
        return Result.success("获取全部数据"+dataName+"成功",list);
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name){

        IPage<Course> page = courseService.findPage(pageNum, pageSize,name);
        return Result.success("查询成功",page);
    }

    @PostMapping
    public Result saveUpdate(@RequestBody Course course){
        Integer idStatus = course.getId();
        courseService.saveUpdate(course);
        String msg = "";
        if(idStatus!=null){
            msg = "修改成功";
        }else{
            msg = "添加成功";
        }
        return Result.success(msg,null);
    }

    @GetMapping("/delete")
    public Result deleteData(@RequestParam("id") Integer id){
        courseService.deleteData(id);
        return Result.success("删除成功",null);
    }

    @PostMapping("/batchDelete")
    public Result batchDelete(@RequestBody List<Integer> ids) {
        courseService.batchDelete(ids);
        return Result.success("删除成功",null);
    }


    @GetMapping("/teacherCourse")
    public Result selectTeacherCourse(@RequestParam("id") Integer id){
        List<Course> list = courseService.selectTeacherCourse(id);
        return Result.success("查询教师课程成功",list);
    }

    @GetMapping("/studentCourse")
    public Result selectStudentCourse(@RequestParam("id") Integer id){
        List<Integer> list = courseService.selectCourseList(id);
        List<Course> courseList = new ArrayList<>();
        if(list.size() != 0){
            courseList = courseService.findCourseByIds(list);
        }
        return Result.success("查询学生已选课程成功",courseList);
    }


    @GetMapping("/selectCourse")
    public Result selectCourse(@RequestParam("courseId") Integer courseId,
                               @RequestParam("username") String username){
        Integer studentId = userService.findUserByUsername(username).getId();
        studentCourseMapper.insert(new StudentCourse(studentId,courseId));
        return Result.success("选课成功",null);

    }
}
