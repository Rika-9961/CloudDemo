package com.hitwh.dto.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Data
public class StudentListVO implements Serializable {
    Long totalElement;
    List<StudentVO> studentList;

    public StudentListVO(Long totalElement, List<StudentVO> studentList) {
        this.totalElement = totalElement;
        this.studentList = studentList;
    }
}