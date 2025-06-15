package com.hitwh.dto.pojo;

import com.hitwh.entity.enumeration.EducationBackgroundType;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.hitwh.entity.Student}
 */

@Value
public class StudentListDTO implements Serializable {
    EducationBackgroundType educationBackground;
    String name;
    String studentId;
    String className;
    int pageNumber;
    int pageSize;
}