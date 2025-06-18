package com.hitwh.feign;

import com.hitwh.dto.vo.StaffVO;
import com.hitwh.entity.Staff;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "service-staff", fallback = StaffFallBack.class)
public interface StaffFeignClient {
    /**
     * 根据员工ID查询员工信息
     *
     * @param id 员工ID
     * @return 员工信息
     */
    @GetMapping("/staff/show_staff/{id}")
    ResponseEntity<Staff> show(@PathVariable Long id);

}
