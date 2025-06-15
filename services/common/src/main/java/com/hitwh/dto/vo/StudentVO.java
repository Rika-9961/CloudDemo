package com.hitwh.dto.vo;


import com.hitwh.entity.Student;
import com.hitwh.entity.enumeration.EducationBackgroundType;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

@Value
public class StudentVO implements Serializable {
    Long id;
    EducationBackgroundType educationBackground;
    String name;
    String sex;
    String studentId;
    String phone;
    Double disScore;
    int disTime;
    LocalDateTime lastTime;
    Boolean deleted = false;
    String className;


    public StudentVO(Student student) {
        this.id = student.getId();
        this.educationBackground = student.getEducationBackground();
        this.name = student.getName();
        this.sex = student.getSex();
        this.studentId = student.getStudentId();
        this.phone = student.getPhone();
        this.disScore = student.getDisScore();
        this.disTime = student.getDisTime();
        this.lastTime = student.getLastTime();
        this.className = student.getClazz().getName();
    }
}
