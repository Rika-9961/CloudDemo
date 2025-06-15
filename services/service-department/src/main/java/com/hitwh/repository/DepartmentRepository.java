package com.hitwh.repository;

import com.hitwh.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findByIdAndDeletedFalse(Long id);

    List<Department> findByDeletedFalse();

    Optional<Department> findByNameAndDeletedFalse(String name);

}