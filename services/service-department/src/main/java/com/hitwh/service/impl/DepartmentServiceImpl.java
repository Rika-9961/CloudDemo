package com.hitwh.service.impl;

import com.hitwh.entity.Department;
import com.hitwh.repository.DepartmentRepository;
import com.hitwh.service.DepartmentService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Resource
    private DepartmentRepository departmentRepository;


    public List<Department> getAllDepartments() {
        return departmentRepository.findByDeletedFalse();
    }

    public void deleteDepartment(Long id) {
        Optional<Department> optional = departmentRepository.findByIdAndDeletedFalse(id);
        if (optional.isPresent()) {
            Department department = optional.get();
            department.setDeleted(true);
            department.setLastTime(LocalDateTime.now());
            departmentRepository.save(department);
        }
    }

    public Department updateDepartment(Department updatedDepartment) {
        Optional<Department> optional = departmentRepository.findByIdAndDeletedFalse(updatedDepartment.getId());
        if (optional.isEmpty()) {
            return null;
        }
        Department department = optional.get();
        Optional.ofNullable(updatedDepartment.getName())
                .ifPresent(department::setName);
        department.setLastTime(LocalDateTime.now());
        return departmentRepository.save(department);
    }

    public Department addDepartment(Department department) {
        Optional<Department> optionalDepartment = departmentRepository.findByNameAndDeletedFalse(department.getName());
        if (optionalDepartment.isPresent()) {
            throw new RuntimeException("部门已存在");
        }
        department.setLastTime(LocalDateTime.now());
        return departmentRepository.save(department);
    }

    public Department getDepartmentsById(Long id) {
        Optional<Department> optional = departmentRepository.findByIdAndDeletedFalse(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new RuntimeException("查无此人");
        }
    }
}
