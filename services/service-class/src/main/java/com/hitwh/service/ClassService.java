package com.hitwh.service;


import com.hitwh.dto.pojo.ClassDTO;
import com.hitwh.dto.vo.ClassListVO;
import com.hitwh.dto.vo.ClassVO;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;


public interface ClassService {


    ClassListVO searchClass(String name, LocalDate startDate, LocalDate endDate, Pageable pageable);

    ClassVO getClassById(Long id);

    void deleteClass(Long id);

    ClassVO addClass(ClassDTO classes);

    ClassVO updateClass(ClassDTO updateClass);

    Integer getTotalNumber();
}
