package com.hitwh.service;

import com.hitwh.dto.pojo.CourseDTO;
import com.hitwh.dto.vo.CourseListVO;
import com.hitwh.dto.vo.CourseVO;
import com.hitwh.entity.enumeration.CourseType;
import org.springframework.data.domain.Pageable;

public interface CourseService {
    CourseListVO searchCourse(String name, CourseType courseType, Pageable pageable);

    CourseVO getCourseById(Long id);

    void deleteCourse(Long id);

    CourseVO addCourse(CourseDTO classes);

    CourseVO updateCourse(CourseDTO updateCourse);

    Integer getTotalNumber();
}
