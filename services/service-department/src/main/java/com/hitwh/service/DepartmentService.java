package com.hitwh.service;

import com.hitwh.entity.Department;

import java.util.List;


public interface DepartmentService {

    List<Department> getAllDepartments();

    void deleteDepartment(Long id) ;

    Department updateDepartment(Department updatedDepartment) ;

    Department addDepartment(Department department) ;

    Department getDepartmentsById(Long id) ;
}
