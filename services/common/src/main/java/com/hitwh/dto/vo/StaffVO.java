package com.hitwh.dto.vo;

import com.hitwh.entity.Staff;
import com.hitwh.entity.enumeration.StaffType;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.hitwh.entity.Staff}
 */
@Value
public class StaffVO implements Serializable {
    Long id;
    String name;
    String staffId;
//    String picture;
    String sex;
    StaffType job;
    LocalDate start;
    LocalDateTime lastTime;
    Long departmentId;
    Boolean deleted;

    public StaffVO(Staff staff) {
        this.id = staff.getId();
        this.name = staff.getName();
        this.staffId = staff.getStaffId();
//        this.picture = staff.getPicture();
        this.sex = staff.getSex();
        this.job = staff.getJob();
        this.start = staff.getStart();
        this.lastTime = staff.getLastTime();
        this.departmentId = staff.getDepartment().getId();
        this.deleted = staff.getDeleted();
    }
}