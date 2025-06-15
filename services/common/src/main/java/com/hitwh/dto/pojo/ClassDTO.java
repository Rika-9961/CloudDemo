package com.hitwh.dto.pojo;

import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link com.hitwh.entity.Class}
 */
@Value
public class ClassDTO implements Serializable {
    Long id;
    String name;
    String address;
    LocalDate start;
    LocalDate end;
    Long teacherId;
}
