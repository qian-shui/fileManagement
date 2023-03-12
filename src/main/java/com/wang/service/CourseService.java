package com.wang.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.wang.pojo.Course;
import com.wang.pojo.Role;

import java.util.ArrayList;
import java.util.List;

public interface CourseService {
    List<Course> findAll();

    IPage<Course> findPage(Integer pageNum, Integer pageSize, String name);

    void saveUpdate(Course course);

    void deleteData(Integer id);

    void batchDelete(List<Integer> ids);

    List<Course> selectTeacherCourse(Integer id);

    List<Course> findCourseByIds(List<Integer> a);

    List<Integer> selectCourseList(Integer id);
}
