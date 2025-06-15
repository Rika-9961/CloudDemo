package com.hitwh.service;

import com.hitwh.dto.pojo.StudentDTO;
import com.hitwh.dto.vo.StudentListVO;
import com.hitwh.dto.vo.StudentVO;
import com.hitwh.entity.enumeration.EducationBackgroundType;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface StudentService {


    StudentListVO searchStudent(String name, String studentId, String classesName, EducationBackgroundType educationBackground, Pageable pageable);

    StudentVO getStudentById(Long id);

    void deleteStudent(Long id);
    void deleteAllStudent(List<Long> id);

    StudentVO addStudent(StudentDTO student);

    StudentVO updateStudent(StudentDTO updateStudent);

    void violateStudent(Double score, Long id);


}
