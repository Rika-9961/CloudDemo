package com.hitwh.dto.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Data
public class ClassListVO implements Serializable {
    Long totalElement;
    List<ClassVO> classList;

    public ClassListVO(Long totalElement, List<ClassVO> classList) {
        this.totalElement = totalElement;
        this.classList = classList;
    }
}
