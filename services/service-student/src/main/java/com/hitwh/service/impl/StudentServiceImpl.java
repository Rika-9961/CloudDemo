package com.hitwh.service.impl;

import com.hitwh.dto.pojo.StudentDTO;
import com.hitwh.dto.vo.StudentListVO;
import com.hitwh.dto.vo.StudentVO;
import com.hitwh.entity.Class;
import com.hitwh.entity.Student;
import com.hitwh.entity.enumeration.EducationBackgroundType;
import com.hitwh.feign.ClassFeignClient;
import com.hitwh.repository.StudentRepository;
import com.hitwh.service.StudentService;
import jakarta.annotation.Resource;
import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService {
    @Resource
    private StudentRepository studentRepository;

    @Resource
    private ClassFeignClient classFeignClient;

    @Resource
    private ModelMapper modelMapper;

    @Override
    public StudentListVO searchStudent(String name, String studentId, String className, EducationBackgroundType educationBackground, Pageable pageable) {
        Specification<Student> specification = ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(name)) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            }
            if (StringUtils.hasText(studentId)) {
                predicates.add(criteriaBuilder.like(root.get("studentId"), "%" + studentId + "%"));
            }
            if (StringUtils.hasText(className)) {
                predicates.add(criteriaBuilder.equal(root.join("clazz").get("name"), className));
            }
            Optional.ofNullable(educationBackground)
                    .map(ebt -> criteriaBuilder.equal(root.get("educationBackground"), ebt))
                    .ifPresent(predicates::add);
            predicates.add(criteriaBuilder.equal(root.get("deleted"), false));
            return query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0]))).getRestriction();
        });
        long total = studentRepository.count(specification);
        Page<StudentVO> map = studentRepository.findAll(specification, pageable).map(StudentVO::new);
        return new StudentListVO(total, map.getContent());
    }

    @Override
    public StudentVO getStudentById(Long id) {
        Optional<Student> optional = studentRepository.findByIdAndDeletedFalse(id);
        if (optional.isEmpty()) {
            throw new RuntimeException("查无此人");
        }
        return new StudentVO(optional.get());
    }

    @Override
    public void deleteStudent(Long id) {
        Optional<Student> optional = studentRepository.findByIdAndDeletedFalse(id);
        if (optional.isPresent()) {
            Student student = optional.get();
            student.setDeleted(true);
            student.setLastTime(LocalDateTime.now());
            studentRepository.save(student);
        }
    }

    @Override
    public void deleteAllStudent(List<Long> ids) {
        List<Student> students = studentRepository.findAllByIdInAndDeletedFalse(ids);
        for (Student student : students) {
            student.setDeleted(true);
            student.setLastTime(LocalDateTime.now());
        }
        studentRepository.saveAll(students);
    }

    @Override
    public StudentVO addStudent(StudentDTO addStudent) {
        Student student = new Student();
        student.setEducationBackground(addStudent.getEducationBackground());
        student.setName(addStudent.getName());
        student.setSex(addStudent.getSex());
        if (studentRepository.existsByStudentIdAndDeletedFalse(addStudent.getStudentId())) {
            log.info("学号重复");
            throw new RuntimeException("学号已重复");
        } else {
            student.setStudentId(addStudent.getStudentId());
        }
        if (studentRepository.existsByPhoneAndDeletedFalse(addStudent.getPhone())) {
            log.info("手机号重复重复");
            throw new RuntimeException("手机号已重复");
        } else {
            student.setPhone(addStudent.getPhone());
        }
        Optional.ofNullable(classFeignClient.show(addStudent.getClassId()).getBody()).ifPresent(student::setClazz);
//        classRepository.findByIdAndDeletedFalse(addStudent.getClassId()).ifPresent(student::setClazz);
        student.setLastTime(LocalDateTime.now());
        Optional<Student> optional = studentRepository.findByNameAndDeletedFalse(student.getName());
        if (optional.isPresent()) {
            throw new RuntimeException("学生已存在");
        }
        return new StudentVO(studentRepository.save(student));
    }

    @Override
    public StudentVO updateStudent(StudentDTO updateStudent) {
        Optional<Student> optional = studentRepository.findByIdAndDeletedFalse(updateStudent.getId());
        if (optional.isEmpty()) {
            return null;
        }
        Student student = optional.get();
        Optional.ofNullable(updateStudent.getName()).ifPresent(student::setName);
        Optional.ofNullable(updateStudent.getEducationBackground()).ifPresent(student::setEducationBackground);
        Optional.ofNullable(updateStudent.getSex()).ifPresent(student::setSex);
        if (studentRepository.existsByIdNotAndPhoneAndDeletedFalse(updateStudent.getId(), updateStudent.getStudentId())) {
            throw new RuntimeException("学号已重复");
        } else {
            Optional.ofNullable(updateStudent.getStudentId()).ifPresent(student::setStudentId);
        }
        if (studentRepository.existsByIdNotAndStudentIdAndDeletedFalse(updateStudent.getId(), updateStudent.getPhone())) {
            throw new RuntimeException("学号已重复");
        } else {
            Optional.ofNullable(updateStudent.getPhone()).ifPresent(student::setPhone);
        }
        Optional.ofNullable(classFeignClient.show(updateStudent.getClassId()).getBody()).ifPresent(student::setClazz);
//        classRepository.findByIdAndDeletedFalse(updateStudent.getClassId()).ifPresent(student::setClazz);
        student.setLastTime(LocalDateTime.now());
        return new StudentVO(studentRepository.save(student));
    }

    @Override
    public void violateStudent(Double score, Long id) {
        Optional<Student> optional = studentRepository.findByIdAndDeletedFalse(id);
        if (optional.isPresent()) {
            Student student = optional.get();
            student.setDisScore(student.getDisScore() + score);
            student.setDisTime(student.getDisTime() + 1);
            studentRepository.save(student);
        }
    }

}
