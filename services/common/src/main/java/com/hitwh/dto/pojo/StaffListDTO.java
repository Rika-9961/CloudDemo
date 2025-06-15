package com.hitwh.dto.pojo;

import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link com.hitwh.entity.Staff}
 */
@Value
public class StaffListDTO implements Serializable {
    String name;
    String sex;
    LocalDate start;
    int pageNumber;
    int pageSize;
}