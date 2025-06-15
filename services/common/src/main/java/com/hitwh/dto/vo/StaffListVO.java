package com.hitwh.dto.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Data
public class StaffListVO implements Serializable {
    Long totalElement;
    List<StaffVO> staffList;

    public StaffListVO(Long totalElement, List<StaffVO> staffList) {
        this.totalElement = totalElement;
        this.staffList = staffList;
    }

}
