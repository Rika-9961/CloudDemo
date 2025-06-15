package com.hitwh.repository;

import com.hitwh.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {
    Optional<Student> findByIdAndDeletedFalse(Long id);

    Optional<Student> findByNameAndDeletedFalse(String name);
    List<Student> findAllByIdInAndDeletedFalse(List<Long> ids);

    boolean existsByPhoneAndDeletedFalse(String phone);

    boolean existsByIdNotAndPhoneAndDeletedFalse(Long id, String phone);
    boolean existsByIdNotAndStudentIdAndDeletedFalse(Long id, String studentId);

    boolean existsByStudentIdAndDeletedFalse(String studentId);

}