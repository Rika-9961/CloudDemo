package com.hitwh.dto.vo;

import com.hitwh.entity.Class;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link com.hitwh.entity.Class}
 */
@Value
public class ClassVO implements Serializable {
    Long id;
    String name;
    String address;
    LocalDate start;
    LocalDate end;
    String teacherName;
    Boolean deleted;

    public ClassVO(Class c) {
        this.id = c.getId();
        this.name = c.getName();
        this.address = c.getAddress();
        this.start = c.getStart();
        this.end = c.getEnd();
        this.teacherName = c.getTeacher().getName();
        this.deleted = c.getDeleted();
    }
}