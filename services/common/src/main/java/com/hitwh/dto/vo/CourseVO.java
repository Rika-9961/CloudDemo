package com.hitwh.dto.vo;

import com.hitwh.entity.Course;
import com.hitwh.entity.Staff;
import com.hitwh.entity.enumeration.CourseType;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO for {@link Course}
 */
@Value
public class CourseVO implements Serializable {
    Long id;
    String name;
    String courseId;
    CourseType courseType;
    String address;
    String time;
    Double classHour;
    Double creditHour;
    Integer capacity;
    List<String> teacherNames;
    LocalDateTime lastTime;
    Boolean deleted;

    public CourseVO(Course course) {
        this.id = course.getId();
        this.name = course.getName();
        this.courseId = course.getCourseId();
        this.courseType = course.getCourseType();
        this.address = course.getAddress();
        this.time = course.getTime();
        this.classHour = course.getClassHour();
        this.creditHour = course.getCreditHour();
        this.capacity = course.getCapacity();
        this.teacherNames = course.getTeachers().stream()
                .map(Staff::getName)
                .collect(Collectors.toList());
        this.lastTime = course.getLastTime();
        this.deleted = course.getDeleted();
    }
}