package com.wang.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("vue_student_course")
@AllArgsConstructor
@NoArgsConstructor
public class StudentCourse {
    private Integer studentId;
    private Integer courseId;
}
