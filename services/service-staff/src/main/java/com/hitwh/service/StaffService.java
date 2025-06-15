package com.hitwh.service;

import com.hitwh.dto.vo.StaffListVO;
import com.hitwh.dto.vo.StaffVO;
import com.hitwh.entity.Staff;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;


public interface StaffService {

    StaffListVO searchStaff(String name, String sex, LocalDate startDate, Pageable pageable);

    StaffVO getStaffById(Long id);

    void deleteStaff(Long id);
    void deleteAllStaff(List<Long> ids);

    StaffVO addStaff(Staff staff);

    StaffVO updateStaff(Staff updateStaff);

    Integer getTotalNumber();
}
