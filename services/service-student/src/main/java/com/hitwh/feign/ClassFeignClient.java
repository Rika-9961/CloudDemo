package com.hitwh.feign;

import com.hitwh.dto.vo.ClassVO;
import com.hitwh.entity.Class;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/classes")
@FeignClient(value = "service-class")
public interface ClassFeignClient {

    @GetMapping("/show_class/{id}")
    ResponseEntity<Class> show(@PathVariable Long id);
}
