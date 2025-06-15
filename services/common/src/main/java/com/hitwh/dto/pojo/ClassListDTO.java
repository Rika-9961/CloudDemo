package com.hitwh.dto.pojo;

import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link com.hitwh.entity.Class}
 */
@Value
public class ClassListDTO implements Serializable {
    String name;
    LocalDate start;
    LocalDate end;
    int pageNumber;
    int pageSize;
}