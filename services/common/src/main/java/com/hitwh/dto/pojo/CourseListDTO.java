package com.hitwh.dto.pojo;

import com.hitwh.entity.enumeration.CourseType;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.hitwh.entity.Course}
 */
@Value
public class CourseListDTO implements Serializable {
    String name;
    CourseType courseType;
    int pageNumber;
    int pageSize;
}