package com.hitwh.repository;


import com.hitwh.entity.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassRepository extends JpaRepository<Class, Long>, JpaSpecificationExecutor<Class> {
    Optional<Class> findByIdAndDeletedFalse(Long id);

    Optional<Class> findByNameAndDeletedFalse(String name);

    List<Class> findAllByNameAndDeletedFalse(String name);

    List<Class> findAllByDeletedFalse();
}