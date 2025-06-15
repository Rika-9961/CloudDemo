package com.hitwh.controller;

import com.hitwh.entity.Department;
import com.hitwh.service.DepartmentService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 部门管理
 */
@Slf4j
@RestController
@RequestMapping("/department")
public class DepartmentController {
    @Resource
    private DepartmentService departmentService;

    /**
     * 部门查询
     *
     * @return 分页查询的部门信息
     */
    @PostMapping("/list")
    public ResponseEntity<List<Department>> getPage() {
        log.info("查询");
        List<Department> departments = departmentService.getAllDepartments();
        return ResponseEntity.ok().body(departments);
    }

    /**
     * 查询回显
     *
     * @param id 部门id
     * @return 该id部门信息
     */

    @GetMapping("/show/{id}")
    public ResponseEntity<Department> show(@PathVariable Long id) {
        Department department = departmentService.getDepartmentsById(id);
        return ResponseEntity.status(HttpStatus.OK).body(department);
    }

    /**
     * 修改部门
     *
     * @param updatedDepartment 修改的部门信息 {@link Department}
     * @return 修改后的部门信息
     */
    @PutMapping("/update")
    public ResponseEntity<Department> update(@RequestBody Department updatedDepartment) {
        Department department = departmentService.updateDepartment(updatedDepartment);
        if (department == null) {
            log.info("查无此人");
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(department);
    }

    /**
     * 删除部门
     *
     * @param id 删除的部门id
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("删除");
        departmentService.deleteDepartment(id);
        return ResponseEntity.ok().build();
    }

    /**
     * 新增部门
     *
     * @param department 学员信息 {@link Department}
     * @return 新增的学员信息
     */
    @PostMapping("/add")
    public ResponseEntity<Department> add(@RequestBody Department department) {
        Department addDepartment = departmentService.addDepartment(department);
        return ResponseEntity.status(HttpStatus.CREATED).body(addDepartment);
    }
}
