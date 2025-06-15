package com.hitwh.dto.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Data
public class CourseListVO implements Serializable {
    Long totalElement;
    List<CourseVO> courseList;

    public CourseListVO(Long totalElement, List<CourseVO> courseList) {
        this.totalElement = totalElement;
        this.courseList = courseList;
    }
}