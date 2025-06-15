package com.hitwh.dto.pojo;

import com.hitwh.entity.enumeration.CourseType;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link com.hitwh.entity.Course}
 */
@Value
public class CourseDTO implements Serializable {
    Long id;
    String name;
    String courseId;
    CourseType courseType;
    String address;
    String time;
    Double classHour;
    Double creditHour;
    Integer capacity;
    List<Long> teacherId;
}