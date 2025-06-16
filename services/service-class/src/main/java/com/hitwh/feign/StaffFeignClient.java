package com.hitwh.feign;

import com.hitwh.dto.vo.StaffVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-staff/staff")
public interface StaffFeignClient {

    @GetMapping("/show/{id}")
    ResponseEntity<StaffVO> show(@PathVariable Long id);

}
