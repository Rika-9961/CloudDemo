package com.hitwh.repository;

import com.hitwh.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long>, JpaSpecificationExecutor<Staff> {

    Optional<Staff> findByIdAndDeletedFalse(Long id);

    Optional<Staff> findByNameAndDeletedFalse(String name);
    List<Staff> findAllByIdInAndDeletedFalse(List<Long> ids);

    List<Staff> findAllByDeletedFalse();

}