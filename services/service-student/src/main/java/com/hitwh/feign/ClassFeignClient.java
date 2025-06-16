package com.hitwh.feign;

import com.hitwh.dto.vo.ClassVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-class/class")
public interface ClassFeignClient {

    @GetMapping("/show/{id}")
    ResponseEntity<ClassVO> show(@PathVariable Long id);
}
