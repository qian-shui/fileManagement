package com.wang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wang.common.Constants;
import com.wang.exception.ServiceException;
import com.wang.mapper.CourseMapper;
import com.wang.mapper.StudentCourseMapper;
import com.wang.pojo.Course;
import com.wang.pojo.FileData;
import com.wang.pojo.Role;
import com.wang.pojo.StudentCourse;
import com.wang.service.CourseService;
import com.wang.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    @Autowired
    private StudentCourseMapper studentCourseMapper;


    @Override
    public List<Course> findAll() {
        try {
            return list();
        }catch (Exception e){
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
    }

    @Override
    public IPage<Course> findPage(Integer pageNum, Integer pageSize, String name) {
        Page<Course> page = new Page<>(pageNum, pageSize);
        return courseMapper.findPage(page,name);
    }

    @Override
    public void saveUpdate(Course course) {
        try {
            saveOrUpdate(course);
        }catch (Exception e){
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
    }

    @Override
    public void deleteData(Integer id) {
        try {
            removeById(id);
        }catch (Exception e){
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
    }

    @Override
    public void batchDelete(List<Integer> ids) {
        try {
            removeByIds(ids);
        }catch (Exception e){
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
    }

    @Override
    public List<Course> selectTeacherCourse(Integer id) {
        try {
            QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("teacher_id",id);
            return list(queryWrapper);
        }catch (Exception e){
            throw new ServiceException(Constants.CODE_500,"系统错误");
        }
    }

    @Override
    public List<Course> findCourseByIds(List<Integer> a) {
        return listByIds(a);
    }

    @Override
    public List<Integer> selectCourseList(Integer id) {
        QueryWrapper<StudentCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("student_id",id);
        List<StudentCourse> studentCourses = studentCourseMapper.selectList(queryWrapper);
        ArrayList<Integer> objects = new ArrayList<>();
        for (StudentCourse studentCours : studentCourses) {
            objects.add(studentCours.getCourseId());
        }
        return objects;
    }
}
