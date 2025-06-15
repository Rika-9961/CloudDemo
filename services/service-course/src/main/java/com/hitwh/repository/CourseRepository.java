package com.hitwh.repository;

import com.hitwh.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>, JpaSpecificationExecutor<Course> {
    Optional<Course> findByIdAndDeletedFalse(Long id);

    Optional<Course> findByNameAndDeletedFalse(String name);

    List<Course> findAllByDeletedFalse();
}
