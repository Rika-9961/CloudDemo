package com.hitwh.dto.pojo;

import com.hitwh.entity.enumeration.EducationBackgroundType;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.hitwh.entity.Student}
 */

@Value
public class StudentDTO implements Serializable {
    Long id;
    EducationBackgroundType educationBackground;
    String name;
    String sex;
    String studentId;
    String phone;
    Long classId;
}
