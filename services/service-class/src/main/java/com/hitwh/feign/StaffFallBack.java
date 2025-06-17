package com.hitwh.feign;

import com.hitwh.entity.Staff;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StaffFallBack implements StaffFeignClient{
    /**
     * 根据员工ID查询员工信息
     *
     * @param id 员工ID
     * @return 员工信息
     */
    @Override
    public ResponseEntity<Staff> show(Long id) {
        log.info("StaffFeignClient show fallback, id: {}", id);
        return null;
    }
}
